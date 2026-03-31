// 🔥 ENTER KEY SUPPORT
function handleKey(event) {
    if (event.key === "Enter") {
        getPrices();
    }
}

/* 🔥 REAL BACKEND COMPARISON */
async function getPrices() {

    console.log("API function triggered");

    let product = document.getElementById("product").value;

    if (!product) {
        alert("Please enter a product");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/products/search?name=${product}`);
        const data = await response.json();

        console.log("Data received:", data);

        // SHOW SECTION
        document.getElementById("compareSection").style.display = "block";

        displayProducts(data);

    } catch (error) {
        console.error("Error fetching data:", error);
        alert("Backend not connected!");
    }
}

/* 🔥 DISPLAY BACKEND DATA */
function displayProducts(products) {

    let container = document.getElementById("productContainer");
    let warning = document.getElementById("warningBanner");

    container.innerHTML = "";

    let showWarning = false;

    products.forEach(p => {

        // ⚠️ CHECK FALLBACK
        if (p.sourceType === "fallback") {
            showWarning = true;
        }

        let isBest = p.bestDeal ? "best-deal" : "";

        // ✅ FIX PRICE (avoid double ₹)
        let price = p.price.includes("₹") ? p.price : "₹" + p.price;

        let card = document.createElement("div");
        card.className = `product-card ${isBest}`;

        card.innerHTML = `
            <div class="product-source">${p.source}</div>
            <div class="product-name">${p.name}</div>
            <div class="product-price">${price}</div>

            ${p.bestDeal ? '<div class="best-tag">🔥 Best Deal</div>' : ''}

            ${p.sourceType === "fallback" 
                ? `<div class="fallback">⚠️ ${p.message}</div>` 
                : ''}
        `;

        container.appendChild(card);
    });

    // ⚠️ GLOBAL WARNING
    if (showWarning) {
        warning.style.display = "block";
    } else {
        warning.style.display = "none";
    }
}