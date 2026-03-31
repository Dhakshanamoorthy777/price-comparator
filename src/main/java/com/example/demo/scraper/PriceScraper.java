package com.example.demo.scraper;

import com.example.demo.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class PriceScraper {

    // 🔥 COMMON CONNECTION METHOD
    private Document connect(String url) throws Exception {
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/120 Safari/537.36")
                .header("Accept-Language", "en-IN,en;q=0.9")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Connection", "keep-alive")
                .header("Upgrade-Insecure-Requests", "1")
                .header("Referer", "https://www.google.com/")
                .header("Cache-Control", "no-cache")
                .timeout(15000)
                .followRedirects(true)
                .get();
    }

    // =========================
    // 🟢 DUMMY DATA (ALWAYS WORKS)
    // =========================
    public List<Product> getDummyProducts(String productName) {

        List<Product> list = new ArrayList<>();

        list.add(new Product(productName + " Premium", "₹52", "LocalMart", false, "real", null));
        list.add(new Product(productName + " Budget Pack", "₹48", "LocalMart", false, "real", null));
        list.add(new Product(productName + " Organic", "₹65", "LocalMart", false, "real", null));

        System.out.println("✅ Dummy products added");

        return list;
    }

    // =========================
    // 🟡 JIOMART SCRAPER
    // =========================
    public List<Product> getJioMartProducts(String productName) {

        List<Product> list = new ArrayList<>();

        try {
            String query = URLEncoder.encode(productName, StandardCharsets.UTF_8);
            String url = "https://www.jiomart.com/search/" + query;

            Document doc = connect(url);

            Elements items = doc.select("div.plp-card-container");

            int count = 0;

            for (Element item : items) {

                if (count >= 5) break;

                String name = item.select("div.plp-card-details-name").text();
                String price = item.select("span.jm-heading-xxs").text();

                if (!name.isEmpty() && !price.isEmpty()) {
                    list.add(new Product(name, price, "JioMart", false, "real", null));
                    count++;
                }
            }

            System.out.println("✅ JioMart items: " + list.size());

        } catch (Exception e) {
            System.out.println("❌ JioMart scraping failed");
        }

        return list;
    }

    // =========================
    // 🟡 AMAZON SCRAPER
    // =========================
    public List<Product> getAmazonProducts(String productName) {

        List<Product> list = new ArrayList<>();

        try {
            String query = URLEncoder.encode(productName, StandardCharsets.UTF_8);
            String url = "https://www.amazon.in/s?k=" + query;

            Document doc = connect(url);

            Elements items = doc.select("div[data-component-type=s-search-result]");

            int count = 0;

            for (Element item : items) {

                if (count >= 5) break;

                String name = item.select("h2 span").text();

                String price = item.select("span.a-price-whole").first() != null
                        ? item.select("span.a-price-whole").first().text()
                        : "";

                if (!name.isEmpty() && !price.isEmpty()) {
                    list.add(new Product(name, price, "Amazon", false, "real", null));
                    count++;
                }
            }

            System.out.println("✅ Amazon items: " + list.size());

        } catch (Exception e) {
            System.out.println("❌ Amazon scraping failed");
        }

        return list;
    }

    // =========================
    // 🔵 FLIPKART SCRAPER
    // =========================
    public List<Product> getFlipkartProducts(String productName) {

        List<Product> list = new ArrayList<>();

        try {
            String query = URLEncoder.encode(productName, StandardCharsets.UTF_8);
            String url = "https://www.flipkart.com/search?q=" + query;

            Document doc = connect(url);

            Elements items = doc.select("div._1AtVbE");

            int count = 0;

            for (Element item : items) {

                if (count >= 5) break;

                String name = item.select("div._4rR01T").text();
                if (name.isEmpty()) {
                    name = item.select("a.s1Q9rs").text();
                }

                String price = item.select("div._30jeq3").first() != null
                        ? item.select("div._30jeq3").first().text()
                        : "";

                if (!name.isEmpty() && !price.isEmpty()) {
                    list.add(new Product(name, price, "Flipkart", false, "real", null));
                    count++;
                }
            }

            System.out.println("✅ Flipkart items: " + list.size());

        } catch (Exception e) {
            System.out.println("❌ Flipkart scraping failed");
        }

        return list;
    }
}