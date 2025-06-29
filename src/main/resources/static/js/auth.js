
  document.getElementById("login-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // ✅ Prevent the default GET request

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {
     const response =
      await fetch("http://localhost:8080/api/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, password })
      });

      if (!response.ok) {
        const errorData = await response.json();
        alert("Login failed: " + (errorData.message || "Invalid credentials"));
        return;
      }

      const token = await response.text();
      localStorage.setItem("token", token);
      alert("Login successful");
      window.location.href = "dashboard.html";

    } catch (error) {
      console.error("Login error:", error);
      alert("An error occurred while logging in.");
    }
  });

