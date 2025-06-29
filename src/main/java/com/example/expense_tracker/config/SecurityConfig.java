package com.example.expense_tracker.config;

import com.example.expense_tracker.FilterPackage.JwtFilter;
import com.example.expense_tracker.Repository.UserRepo;
import com.example.expense_tracker.Service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private UserRepo repo;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

      return  http.csrf(customizer  ->customizer
                      .ignoringRequestMatchers("/h2-console/**") // allow H2 console
                      .disable())
              .cors(cors -> {}) // 👈 Enable CORS using the bean below
              .headers(headers -> headers.frameOptions(frame -> frame.disable()))         // 2. Enable iframe
              .authorizeHttpRequests(request -> request
                      .requestMatchers("/api/register","/api/login","/h2-console/**")
                      .permitAll()
                      .requestMatchers("/api/admin/**").hasRole("ADMIN")
                      .requestMatchers("/api/user/**","/api/transaction/**","/api/category/**")
                      .hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
              //diasabling session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

//       Customizer< CsrfConfigurer<HttpSecurity>> custCsrf= new Customizer<CsrfConfigurer<HttpSecurity>>() {
//           @Override
//           public void customize(CsrfConfigurer<HttpSecurity> customizer) {
//               customizer.disable();
//
//           }
//       };

//    //   http.csrf(custCsrf);
//      // Customizer< CsrfConfigurer<HttpSecurity>> custCsrf=
//        //               (customizer) -> customizer.disable();

    }

    /* The AuthenticationManger talk to AuthenticationProvider*/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return  provider;

    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:63342")); // or "*"
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
