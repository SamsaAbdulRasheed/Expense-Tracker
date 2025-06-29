
function getAuthHeaders() {
    return {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + localStorage.getItem("token")
    };
}

document.getElementById("register-form")
    .addEventListener("submit", async function
     (event){ event.preventDefault(); // Stop form from refreshing the page

  const username = document.getElementById("username").value.trim();
  const  email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value.trim();

  if (!username || !password || !email) {
    alert("Please fill out both fields.");
    return;
  }
    const data = {
    username,
    email,
    password };

    const url = `http://localhost:8080/api/register`;
    try{
    const response = await fetch( url ,{
    method : "POST",
    headers : getAuthHeaders(),
    body : JSON.stringify(data)
    });

    if (!response.ok) {
          const errorData = await response.json();
          alert("Registration failed: " + (errorData.message || "Invalid input"));
          return;
        }

    alert("registered Successfully!");
        window.location.href = "index.html";

    }
    catch(err){
    console.error("Registering Error", err);
     alert("An error occurred while registering user.");
    }

});