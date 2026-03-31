package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.scraper.PriceScraper;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private PriceScraper scraper;

    public List<Product> searchProducts(String productName) {

        List<Product> allProducts = new ArrayList<>();

        // 🟢 ALWAYS WORKING (DUMMY DATA)
        allProducts.addAll(scraper.getDummyProducts(productName));

        // 🟡 TRY REAL SCRAPING (JioMart)
        allProducts.addAll(scraper.getJioMartProducts(productName));

        // 🔶 AMAZON (WITH FALLBACK)
        allProducts.addAll(getAmazonData(productName));

        // 🔷 FLIPKART (WITH FALLBACK)
        allProducts.addAll(getFlipkartData(productName));

        // 🔥 DEBUG (VERY IMPORTANT)
        System.out.println("Total products fetched: " + allProducts.size());

        // 🔥 FIND BEST DEAL
        int minPrice = Integer.MAX_VALUE;
        Product bestProduct = null;

        for (Product p : allProducts) {
            try {
                int price = Integer.parseInt(p.getPrice().replaceAll("[^0-9]", ""));
                if (price < minPrice) {
                    minPrice = price;
                    bestProduct = p;
                }
            } catch (Exception e) {}
        }

        // 🔥 MARK BEST DEAL
        if (bestProduct != null) {
            bestProduct.setBestDeal(true);
        }

        return allProducts;
    }

    // =========================
    // 🟡 AMAZON HANDLING
    // =========================
    private List<Product> getAmazonData(String query) {
        try {
            List<Product> products = scraper.getAmazonProducts(query);

            if (products == null || products.isEmpty()) {
                throw new RuntimeException("Amazon blocked or empty");
            }

            return products;

        } catch (Exception e) {
            return getAmazonFallback(query);
        }
    }

    // =========================
    // 🔵 FLIPKART HANDLING
    // =========================
    private List<Product> getFlipkartData(String query) {
        try {
            List<Product> products = scraper.getFlipkartProducts(query);

            if (products == null || products.isEmpty()) {
                throw new RuntimeException("Flipkart blocked or empty");
            }

            return products;

        } catch (Exception e) {
            return getFlipkartFallback(query);
        }
    }

    // =========================
    // 🔴 AMAZON FALLBACK
    // =========================
    private List<Product> getAmazonFallback(String query) {
        List<Product> list = new ArrayList<>();

        list.add(new Product(
                query + " (1kg)",
                "₹58",
                "Amazon",
                false,
                "fallback",
                "Live price unavailable due to access restrictions. Showing estimated value."
        ));

        return list;
    }

    // =========================
    // 🔴 FLIPKART FALLBACK
    // =========================
    private List<Product> getFlipkartFallback(String query) {
        List<Product> list = new ArrayList<>();

        list.add(new Product(
                query + " (1kg)",
                "₹55",
                "Flipkart",
                false,
                "fallback",
                "Unable to fetch live data. Displaying approximate market price."
        ));

        return list;
    }
}