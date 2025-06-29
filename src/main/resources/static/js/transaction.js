
function getAuthHeaders() {
    return {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + localStorage.getItem("token")
    };
}
document.getElementById("add-category").addEventListener("click", async () => {
        console.log("button Clicked");

    const name = document.getElementById("category").value.trim().toLowerCase();
        const type = document.getElementById("type").value.trim().toUpperCase();

 if (!name) {
    alert("Please enter a category name");
    return;
  }
  if (!type) {
      alert("Please enter a category type");
      return;

    }

        const data = {
        name,
        type };

   const  url=`http://localhost:8080/api/category`;
   try{
       const response = await fetch(url,{
        method: "POST",
        headers: getAuthHeaders(),
        body: JSON.stringify(data)
        });

        if(!response.ok){
        const errorData = await response.json();
        alert("Category Creation Failed: " + (errorData.message));
        return;
        }

        alert("Category Created Successfully!");
        document.getElementById("category").value="";
        document.getElementById("type").value="";
        await loadCategories();
        }
        catch(err){
        console.error("Error adding category:", err);
        alert("An error occurred while adding the category.");
  }
        });

document.getElementById("transaction-form").addEventListener("submit", async function (event){
        event.preventDefault(); // Stop form from refreshing the page

console.log("Filter button clicked");
    const amount = document.getElementById("amount").value;
    const date = document.getElementById("date").value;
    const note = document.getElementById("note").value;
    const categoryId = document.getElementById("cat-list").value;


    const data ={
    amount,
    date,
    note,
    categoryId };


    const url = `http://localhost:8080/api/transaction`
    try{
    const response = await fetch( url ,{
                method: "POST",
                headers:
                        getAuthHeaders(),

                body: JSON.stringify(data)
        });

       if(!response.ok){
        const errorData = await response.json();
               alert("Transaction Creation Failed: " + (errorData.message));
               return;
       }


       alert("Transaction Added Successfully!");
       window.location.href = "dashboard.html";
       }
       catch(error){
       console.error("Error adding transaction:", error);
       alert("An error occurred while adding the transaction.");
       }
});

