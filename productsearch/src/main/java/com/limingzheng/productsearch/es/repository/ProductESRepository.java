package com.limingzheng.productsearch.es.repository;

import com.limingzheng.productsearch.es.entity.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductESRepository extends ElasticsearchRepository<ProductDocument, Long> {
}
