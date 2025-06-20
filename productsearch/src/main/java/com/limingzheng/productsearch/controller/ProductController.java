package com.limingzheng.productsearch.controller;

import com.limingzheng.productsearch.entity.Product;
import com.limingzheng.productsearch.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public Page<Product> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return productService.searchProducts(keyword, category, minPrice, maxPrice, pageable);
    }

    /**
     * Search and filter products based on keyword.
     * @param keyword Search keyword for title or description.
     * @return A list of matching products.
     */
    @GetMapping("/keyword/{keyword}")
    public Page<Product> searchProductsByKeyword(
            @PathVariable String keyword,
            @PageableDefault(size = 10) Pageable pageable) {
        return productService.findByKeyword(keyword, pageable);
    }

    /**
     * Search and filter products based on category.
     * @param category Product category to filter by
     * @return A list of matching products.
     */
    @GetMapping("/category/{category}")
    public Page<Product> searchProductsByCategory(
            @PathVariable String category,
            @PageableDefault(size = 10) Pageable pageable) {
        return productService.findByCategory(category, pageable);
    }

    /**
     * Search and filter products based on category.
     * @param minPrice Minimum price to filter by
     * @param maxPrice Maximum price to filter by
     * @return A list of matching products.
     */
    @GetMapping("/price-range")
    public Page<Product> searchProductsByCategory(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return productService.findByPriceRange(minPrice, maxPrice, pageable);
    }


}
