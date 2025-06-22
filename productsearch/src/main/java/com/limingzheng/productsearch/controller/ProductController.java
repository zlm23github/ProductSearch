package com.limingzheng.productsearch.controller;

import com.limingzheng.productsearch.entity.Product;
import com.limingzheng.productsearch.es.entity.ProductDocument;
import com.limingzheng.productsearch.es.service.ProductESService;
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
    private final ProductESService productESService;

    public ProductController(ProductService productService, ProductESService productESService) {
        this.productService = productService;
        this.productESService = productESService;
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
     * Searches for products in Elasticsearch based on multiple criteria.
     *
     * @param keyword  The keyword for full-text search within the 'title' and 'description' fields.
     * @param category The product category to filter by.
     * @param minPrice The minimum price for the range filter.
     * @param maxPrice The maximum price for the range filter.
     * @param pageable Pagination and sorting information automatically provided by Spring Data.
     * @return Paginated Elasticsearch product documents (Page<ProductDocument>)
     */
    @GetMapping("/search-es")
    public Page<ProductDocument> searchESProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Pageable pageable) {

        return productESService.searchProducts(keyword, category, minPrice, maxPrice, pageable);
    }

}
