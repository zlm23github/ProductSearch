package com.limingzheng.productsearch.service;

import com.limingzheng.productsearch.entity.Product;
import com.limingzheng.productsearch.es.entity.ProductDocument;
import com.limingzheng.productsearch.es.repository.ProductESRepository;
import com.limingzheng.productsearch.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DataSyncService {
    private final ProductRepository productRepository;
    private final ProductESRepository productESRepository;

    DataSyncService(ProductRepository productRepository, ProductESRepository productESRepository) {
        this.productRepository = productRepository;
        this.productESRepository = productESRepository;
    }

    /**
     * Fetch data from mysql and save to ES
     */
    public void syncAllProducts() {
        try {
            log.info("Starting full data synchronization from MySQL to Elasticsearch...");
            List<Product> allProducts = productRepository.findAll();

            if(allProducts.isEmpty()) {
                log.warn("No products found in the database. Sync finished.");
                return;
            }

            //convert product to productDocument
            List<ProductDocument> productDocuments = new ArrayList<>();
            for (Product product : allProducts) {
                ProductDocument document = convertToDocument(product);
                productDocuments.add(document);
            }
            log.info("Converted {} products to Elasticsearch documents.", productDocuments.size());

            //Batch saving to Elasticsearch
            int batchSize = 1000;
            for (int i = 0; i < productDocuments.size(); i += batchSize) {
                List<ProductDocument> batch = productDocuments.subList(i, Math.min(i + batchSize, productDocuments.size()));
                productESRepository.saveAll(batch);
                log.info("Synced batch {} - {}", i, Math.min(i + batchSize, productDocuments.size()));
            }
            log.info("Successfully saved {} documents to Elasticsearch.", productDocuments.size());

        } catch (Exception e) {
            log.error("Data sync failed!", e);
            throw new RuntimeException("Data sync failed: " + e.getMessage(), e);
        }

    }

    /**
     * method convert Product to ES's ProductDocument
     * @param product
     * @return ProductDocument
     */
    private ProductDocument convertToDocument(Product product) {
        ProductDocument doc = new ProductDocument();
        doc.setId(product.getId());
        doc.setTitle(product.getTitle());
        doc.setDescription(product.getDescription());
        doc.setCategory(product.getCategory());
        doc.setPrice(product.getPrice());
        return doc;
    }

}
