package com.limingzheng.productsearch.controller;

import com.limingzheng.productsearch.entity.Product;
import com.limingzheng.productsearch.es.entity.ProductDocument;
import com.limingzheng.productsearch.es.service.ProductESService;
import com.limingzheng.productsearch.service.DataSyncService;
import com.limingzheng.productsearch.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@Tag(name = "Product API", description = "Endpoints for managing and searching products")
public class ProductController {
    private final ProductService productService;
    private final ProductESService productESService;
    private final DataSyncService dataSyncService;

    public ProductController(ProductService productService, ProductESService productESService, DataSyncService dataSyncService) {
        this.productService = productService;
        this.productESService = productESService;
        this.dataSyncService = dataSyncService;
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
    @Operation(summary = "Search products using ElasticSearch", description = "Search and filter products based on various criteria using ES")
    public Page<ProductDocument> searchESProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Pageable pageable) {

        return productESService.searchProducts(keyword, category, minPrice, maxPrice, pageable);
    }

    /**
    * Triggers a full data synchronization from the database to Elasticsearch.
    * @return A confirmation message indicating the process has started.
    */
    @PostMapping("/sync")
    @Operation(summary = "Sync data from DB to Elasticsearch", description = "Triggers a full synchronization of product data from the primary database to Elasticsearch. This can be a long-running operation.")
    public ResponseEntity<String> syncData() {
        dataSyncService.syncAllProducts();
        return ResponseEntity.ok("Data synchronization to Elasticsearch has been initiated.");
    }

}
