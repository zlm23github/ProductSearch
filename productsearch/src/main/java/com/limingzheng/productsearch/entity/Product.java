package com.limingzheng.productsearch.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@Schema(description = "Product entity")
public class Product {
    @Id
    @Schema(description = "Product ID", example = "1")
    private Long id;

    @Schema(description = "Product title", example = "iPhone 15")
    private String title;
    
    @Schema(description = "Product description", example = "Latest iPhone model")
    private String description;
    
    @Schema(description = "Product price", example = "999.99")
    private Double price;
    
    @Schema(description = "Product category", example = "Electronics")
    private String category;
}
