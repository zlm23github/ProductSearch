package com.limingzheng.productsearch.repository;

import com.limingzheng.productsearch.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Combined query: keyword + category + price range
    @Query(value = "SELECT * FROM product p WHERE " +
            "(:keyword IS NULL OR MATCH(p.title, p.description) AGAINST (:keyword IN BOOLEAN MODE)) " +
            "AND (:category IS NULL OR p.category = :category) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice)",
            countQuery = "SELECT count(*) FROM product p WHERE " +
                    "(:keyword IS NULL OR MATCH(p.title, p.description) AGAINST (:keyword IN BOOLEAN MODE)) " +
                    "AND (:category IS NULL OR p.category = :category) " +
                    "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
                    "AND (:maxPrice IS NULL OR p.price <= :maxPrice)",
            nativeQuery = true)
    Page<Product> searchProducts(
            @Param("keyword") String keyword,
            @Param("category") String category,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable
    );

}
