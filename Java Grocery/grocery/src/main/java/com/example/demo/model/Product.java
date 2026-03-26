package com.example.demo.model;

import jakarta.persistence.*;

@Entity   // 🔥 THIS IS MUST
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String price;
    private String source;

    public Product() {}

    public Product(String name, String price, String source) {
        this.name = name;
        this.price = price;
        this.source = source;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}