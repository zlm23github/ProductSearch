package com.limingzheng.productsearch.controller;

import com.limingzheng.productsearch.entity.Product;
import com.limingzheng.productsearch.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@Tag(name = "Product API", description = "Endpoints for managing and searching products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by its ID")
    public Product getProductById(@Parameter(description = "Product ID") @PathVariable Long id) {
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
    @Operation(summary = "Search products", description = "Search and filter products based on various criteria")
    public Page<Product> searchProducts(
            @Parameter(description = "Search keyword for title or description") @RequestParam(required = false) String keyword,
            @Parameter(description = "Product category to filter by") @RequestParam(required = false) String category,
            @Parameter(description = "Minimum price to filter by") @RequestParam(required = false) Double minPrice,
            @Parameter(description = "Maximum price to filter by") @RequestParam(required = false) Double maxPrice,
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
    @Operation(summary = "Search products by keyword", description = "Search products based on keyword in title or description")
    public Page<Product> searchProductsByKeyword(
            @Parameter(description = "Search keyword") @PathVariable String keyword,
            @PageableDefault(size = 10) Pageable pageable) {
        return productService.findByKeyword(keyword, pageable);
    }

    /**
     * Search and filter products based on category.
     * @param category Product category to filter by
     * @return A list of matching products.
     */
    @GetMapping("/category/{category}")
    @Operation(summary = "Search products by category", description = "Search products based on category")
    public Page<Product> searchProductsByCategory(
            @Parameter(description = "Product category") @PathVariable String category,
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
    @Operation(summary = "Search products by price range", description = "Search products within a specific price range")
    public Page<Product> searchProductsByCategory(
            @Parameter(description = "Minimum price") @RequestParam Double minPrice,
            @Parameter(description = "Maximum price") @RequestParam Double maxPrice,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return productService.findByPriceRange(minPrice, maxPrice, pageable);
    }


}
