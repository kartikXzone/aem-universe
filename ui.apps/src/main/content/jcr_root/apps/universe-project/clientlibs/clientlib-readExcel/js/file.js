document.addEventListener("DOMContentLoaded", function () {
  fetch("/bin/universe/readExcel")
    .then(response => {
      if (!response.ok) {
        throw new Error("Request failed: " + response.status);
      }
      return response.json();
    })
    .then(dataList => {
      const container = document.getElementById("excel-output");
      if (!container) return;

      if (dataList.length === 0) {
        container.innerHTML = "<p>No data found.</p>";
        return;
      }

      const table = document.createElement("table");
      table.border = "1";
      table.cellPadding = "8";

      const headers = Object.keys(dataList[0]);
      const headerRow = document.createElement("tr");

      headers.forEach(header => {
        const th = document.createElement("th");
        th.textContent = header;
        headerRow.appendChild(th);
      });
      table.appendChild(headerRow);

      dataList.forEach(row => {
        const tr = document.createElement("tr");
        headers.forEach(header => {
          const td = document.createElement("td");
          td.textContent = row[header];
          tr.appendChild(td);
        });
        table.appendChild(tr);
      });

      container.appendChild(table);
    })
    .catch(error => {
      console.error("Error fetching Excel data:", error);
    });
});
