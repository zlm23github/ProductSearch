package com.limingzheng.productsearch.repository;

import com.limingzheng.productsearch.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //Keyword fuzzy search (title or description)
    List<Product> findByKeyword(String keyword);

    //Filter by category
    List<Product> findByCategory(String category);

    //Filter by price interval
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    // Combined query: keyword + category + price range
    @Query("SELECT p FROM Product p WHERE " +
            "(:keyword IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:category IS NULL OR p.category = :category) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> searchProducts(
            @Param("keyword") String keyword,
            @Param("category") String category,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );

}
