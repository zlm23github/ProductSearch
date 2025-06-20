package com.limingzheng.productsearch.controller;

import com.limingzheng.productsearch.entity.Product;
import com.limingzheng.productsearch.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    /**
     * Search and filter products based on various criteria.
     * @param keyword  Search keyword for title or description (optional).
     * @param category Product category to filter by (optional).
     * @param minPrice Minimum price to filter by (optional).
     * @param maxPrice Maximum price to filter by (optional).
     * @return A list of matching products.
     */
    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return productService.searchProducts(keyword, category, minPrice, maxPrice);
    }

    /**
     * Search and filter products based on keyword.
     * @param keyword Search keyword for title or description.
     * @return A list of matching products.
     */
    @GetMapping("/keyword/{keyword}")
    public List<Product> searchProductsByKeyword(@PathVariable String keyword) {
        return productService.findByKeyword(keyword);
    }

    /**
     * Search and filter products based on category.
     * @param category Product category to filter by
     * @return A list of matching products.
     */
    @GetMapping("/category/{category}")
    public List<Product> searchProductsByCategory(@PathVariable String category) {
        return productService.findByCategory(category);
    }

    /**
     * Search and filter products based on category.
     * @param minPrice Minimum price to filter by
     * @param maxPrice Maximum price to filter by
     * @return A list of matching products.
     */
    @GetMapping("/price-range")
    public List<Product> searchProductsByCategory(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice
    ) {
        return productService.findByPriceRange(minPrice, maxPrice);
    }


}
