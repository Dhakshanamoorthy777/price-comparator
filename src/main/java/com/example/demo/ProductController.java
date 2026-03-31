package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    // 🔥 MAIN SEARCH + COMPARE API
    @GetMapping("/search")
    public List<Product> search(@RequestParam(required = false) String name) {

        // ✅ Handle empty input (professional touch)
        if (name == null || name.trim().isEmpty()) {
            return List.of(); // return empty list instead of error
        }

        return service.searchProducts(name.trim());
    }
}