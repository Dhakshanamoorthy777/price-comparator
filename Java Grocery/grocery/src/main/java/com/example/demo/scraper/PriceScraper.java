package com.example.demo.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class PriceScraper {

    // 🔹 AMAZON SCRAPER
    public String getAmazonPrice(String product) {
        try {
            String url = "https://www.amazon.in/s?k=" + product.replace(" ", "+");

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Connection", "keep-alive")
                    .timeout(10000)
                    .get();

            Elements price = doc.select(".a-price-whole");

            if (!price.isEmpty()) {
                return "₹" + price.first().text();
            }

        } catch (Exception e) {
            System.out.println("Amazon blocked or error");
        }

        return "Not Available";
    }

    // 🔹 FLIPKART SCRAPER
    public String getFlipkartPrice(String product) {
        try {
            String url = "https://www.flipkart.com/search?q=" + product.replace(" ", "+");

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .header("Accept-Language", "en-US,en;q=0.9")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Connection", "keep-alive")
                    .timeout(10000)
                    .get();

            Elements price = doc.select("._30jeq3");

            if (!price.isEmpty()) {
                return price.first().text();
            }

        } catch (Exception e) {
            System.out.println("Flipkart blocked or error");
        }

        return "Not Available";
    }
}