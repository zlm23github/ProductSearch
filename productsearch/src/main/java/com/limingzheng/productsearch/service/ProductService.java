package com.limingzheng.productsearch.service;

import com.limingzheng.productsearch.entity.Product;
import com.limingzheng.productsearch.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findByKeyword(String keyword) {
        return productRepository.findByKeyword(keyword);
    }

    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> findByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Product> searchProducts(String keyword, String category, Double minPrice, Double maxPrice) {
        return productRepository.searchProducts(keyword, category, minPrice, maxPrice);
    }

}
