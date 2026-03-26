function extractNumber(price) {
    return parseFloat(price.replace(/[^0-9.]/g, ""));
}

function getPrices() {

    let product = document.getElementById("product").value.trim();

    if (product === "") {
        alert("Please enter a product name");
        return;
    }

    let loading = document.getElementById("loading");
    loading.style.display = "block";
    loading.innerText = "⏳ Loading...";

    fetch(`http://localhost:8080/products/compare?name=${product}`)
    .then(res => {
        if (!res.ok) {
            throw new Error("HTTP error " + res.status);
        }
        return res.json();
    })
    .then(data => {

        let amazon = data.Amazon || "Not Available";
        let flipkart = data.Flipkart || "Not Available";

        // ✅ UPDATE TEXT (NEW UI)
        document.getElementById("amazonText").innerText = "Amazon: " + amazon;
        document.getElementById("flipkartText").innerText = "Flipkart: " + flipkart;

        let amazonCard = document.getElementById("amazon");
        let flipkartCard = document.getElementById("flipkart");

        // Remove old highlight
        amazonCard.classList.remove("cheapest");
        flipkartCard.classList.remove("cheapest");

        // ✅ Highlight cheapest
        if (data.Cheapest === "Amazon") {
            amazonCard.classList.add("cheapest");
        } 
        else if (data.Cheapest === "Flipkart") {
            flipkartCard.classList.add("cheapest");
        } 
        else {
            amazonCard.classList.add("cheapest");
            flipkartCard.classList.add("cheapest");
        }

        // ✅ Calculate savings
        let a = extractNumber(amazon);
        let f = extractNumber(flipkart);

        if (!isNaN(a) && !isNaN(f)) {
            let savings = Math.abs(a - f);

            if (savings > 0) {
                loading.innerText = `💰 You save ₹${savings.toFixed(2)} on ${data.Cheapest}`;
            } else {
                loading.innerText = "Same price on both platforms";
            }
        } else {
            loading.innerText = "";
        }

    })
    .catch(err => {
        loading.style.display = "none";
        console.error(err);
        alert("Error fetching data");
    });
}