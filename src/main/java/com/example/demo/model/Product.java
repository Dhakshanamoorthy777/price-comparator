package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String price;
    private String source;

    // 🔥 Existing
    private boolean bestDeal;

    // 🔥 NEW FIELDS (IMPORTANT)
    private String sourceType; // real / fallback
    private String message;    // reason if fallback

    public Product() {}

    // ✅ OLD CONSTRUCTOR (keep for compatibility)
    public Product(String name, String price, String source) {
        this.name = name;
        this.price = price;
        this.source = source;
        this.bestDeal = false;
        this.sourceType = "real";
        this.message = null;
    }

    // ✅ EXISTING CONSTRUCTOR
    public Product(String name, String price, String source, boolean bestDeal) {
        this.name = name;
        this.price = price;
        this.source = source;
        this.bestDeal = bestDeal;
        this.sourceType = "real";
        this.message = null;
    }

    // 🔥 NEW CONSTRUCTOR (FOR FALLBACK)
    public Product(String name, String price, String source, boolean bestDeal, String sourceType, String message) {
        this.name = name;
        this.price = price;
        this.source = source;
        this.bestDeal = bestDeal;
        this.sourceType = sourceType;
        this.message = message;
    }

    // Getters & Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public boolean isBestDeal() { return bestDeal; }
    public void setBestDeal(boolean bestDeal) { this.bestDeal = bestDeal; }

    public String getSourceType() { return sourceType; }
    public void setSourceType(String sourceType) { this.sourceType = sourceType; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}