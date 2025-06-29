document.addEventListener("DOMContentLoaded", function () {
    fetchTransactionSummary();
    fetchFilteredTransactions(); // Optional: load all on first load
//    transaction.html
        loadCategories(); // 👈 Load category dropdown on load

});

function getAuthHeaders() {
    return {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + localStorage.getItem("token")
    };
}

 async function fetchTransactionSummary() {
try{
   const response = await fetch("http://localhost:8080/api/transaction/summary", {
        headers: getAuthHeaders()
    });

     if (!response.ok) {
      throw new Error("Failed to fetch summary");
 // return response.json(); // ❌ This will never run

            }
            const data = await response.json();
             document.getElementById("total-income").innerText = `Income: ₹${data.totalIncome}`;
                    document.getElementById("total-expense").innerText = `Expense: ₹${data.totalExpense}`;
                    document.getElementById("net-balance").innerText = `Balance: ₹${data.netBalance}`;
         }
      catch (error){
      console.error("Error fetching summary:", error);
      }
  }


 async function fetchFilteredTransactions(filters = {}) {

    const params = new URLSearchParams(filters).toString();
    const url = `http://localhost:8080/api/transaction/filter?${params}`;

try{
    const response = await fetch(url, {
        headers: getAuthHeaders()
    })
    if (!response.ok) { throw new Error("Failed to fetch transactions");
 // return response.json(); // ❌ This will never run
            }


    const data = await response.json();

    const tbody = document.getElementById("transaction-list");
            tbody.innerHTML = "";

            data.forEach((tx,index) => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${tx.categoryName}</td>
                    <td> ₹${tx.amount}</td>
                     <td> ${tx.type}</td>
                      <td> ${tx.date}</td>
                      <td> ${tx.note}</td>
                      <td>
                      <button class="btn btn-sm btn-warning">Edit</button>
                      </td>
                      <td>
                      <button class="btn btn-sm btn-danger">Delete</button>
                      </td> `;
                tbody.appendChild(row);
            });

 if (data.length === 0) {

      const noRow = document.createElement("tr");
      const noDataCell = document.createElement("td");
      noDataCell.setAttribute("colspan", 7); // Spans all columns
      noDataCell.classList.add("text-center", "text-muted");
      noDataCell.textContent = "No transactions found.";
      noRow.appendChild(noDataCell);
      tbody.appendChild(noRow);
      return;
    }
}
catch(error){
console.error("Error fetching transactions:", error);
}

}

// Example usage: call with filters
document.getElementById("filter-btn").addEventListener("click", () => {
    console.log("Filter button clicked"); // Add this line

    const type = document.getElementById("type-filter").value.toUpperCase();
    const category = document.getElementById("category-filter").value;
    const startDate = document.getElementById("start-date").value;
    const endDate = document.getElementById("end-date").value;
const filters = {};
  if (type) filters.type = type;
  if (category) filters.category = category;
  if (startDate) filters.startDate = startDate;
  if (endDate) filters.endDate = endDate;

    fetchFilteredTransactions(filters);
});



//  in tansaction.html to fetch category name
  async function loadCategories() {
    const username = localStorage.getItem("username");
try{
  const response = await fetch(`http://localhost:8080/api/category`,

     {
        headers: getAuthHeaders()
    });

    const data = await response.json();
      const select = document.getElementById("cat-list");
      select.innerHTML = ""; // Clear previous options

            data.forEach(cat => {
            const option = document.createElement("option");
            option.value=cat.id;
            option.text = `${cat.name}`;
            select.appendChild(option);
            });
}

catch(error){
console.error("Error fetching category:", error);
}
 }

