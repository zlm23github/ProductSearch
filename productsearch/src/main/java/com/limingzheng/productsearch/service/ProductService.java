package com.limingzheng.productsearch.service;

import com.limingzheng.productsearch.entity.Product;
import com.limingzheng.productsearch.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> searchProducts(String keyword, String category, Double minPrice, Double maxPrice, Pageable pageable) {
        return productRepository.searchProducts(keyword, category, minPrice, maxPrice, pageable);
    }

}
