package com.limingzheng.productsearch.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    private long id;

    private String title;
    private String description;
    private Double price;
    private String category;
}
