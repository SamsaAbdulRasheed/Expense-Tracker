package com.example.expense_tracker.FilterPackage;

import com.example.expense_tracker.Service.JWTService;
import com.example.expense_tracker.Service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

//    @Autowired
//    ApplicationContext context;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        this what you get from the client side
        // Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aW51IiwiaWF0IjoxNzQ5OTU4NzU4LCJleHAiOjE3NDk5NTg4NjZ9.ShUI63idGOSv9iDo1_vklL8kuLhoZjnUoGuI0ILq408

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        String role = null;


        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
//            role = jwtService.extractRole(token);

        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails=context.getBean(MyUserDetailsService.class)
//                    .loadUserByUsername(username);

//            SimpleGrantedAuthority authority=
//                    new SimpleGrantedAuthority("ROLE_"+role);

            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);

            if (jwtService.validateToken(token, userDetails)) {

                //create authentication object for SecurityContext
/* "This user (userDetails) has already been authenticated via JWT.
 Here are their roles/authorities. Let them access the secured resources." */
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        //passing to next filter
        filterChain.doFilter(request, response);
    }
}
