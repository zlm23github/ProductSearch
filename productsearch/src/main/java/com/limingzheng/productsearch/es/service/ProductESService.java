package com.limingzheng.productsearch.es.service;


import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;
import com.limingzheng.productsearch.es.entity.ProductDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.ArrayList;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProductESService {

    private final ElasticsearchOperations elasticsearchOperations;

    public Page<ProductDocument> searchProducts(String keyword, String category, Double minPrice, Double maxPrice, Pageable pageable) {
        try{
            //create BoolQuery builder
            BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

            if (StringUtils.hasText(keyword)) {
                MultiMatchQuery multiMatchQuery = new MultiMatchQuery.Builder()
                        .query(keyword)
                        .fields("title", "description")
                        .build();
                boolQueryBuilder.must(multiMatchQuery._toQuery());
            }

            if (StringUtils.hasText(category)) {
                TermQuery termQuery = new TermQuery.Builder()
                        .field("category.keyword")
                        .value(category)
                        .build();
                boolQueryBuilder.filter(termQuery._toQuery());
            }

            if (minPrice != null || maxPrice != null) {
                RangeQuery.Builder rangeQueryBuilder = new RangeQuery.Builder().field("price");
                if (minPrice != null) {
                    rangeQueryBuilder.gte(JsonData.of(minPrice));
                }
                if (maxPrice != null) {
                    rangeQueryBuilder.lte(JsonData.of(maxPrice));
                }
                boolQueryBuilder.filter(rangeQueryBuilder.build()._toQuery());
            }

            //create final BoolQuery object
            BoolQuery finalBoolQuery = boolQueryBuilder.build();

            Query finalQuery;
            if (!finalBoolQuery.hasClauses()) {
                finalQuery = new MatchAllQuery.Builder().build()._toQuery();
            } else {
                finalQuery = finalBoolQuery._toQuery();
            }

            //Build a local query and include pagination details
            NativeQuery nativeQuery = NativeQuery.builder()
                    .withQuery(finalQuery)
                    .withPageable(pageable)
                    .build();

            SearchHits<ProductDocument> searchHits = elasticsearchOperations.search(nativeQuery, ProductDocument.class);

            List<ProductDocument> productList = new ArrayList<>();
            for (SearchHit<ProductDocument> hit : searchHits) {
                productList.add(hit.getContent());
            }

            return new PageImpl<>(productList, pageable, searchHits.getTotalHits());

        }catch (Exception e) {
            log.error("Elasticsearch search failed!", e);
            throw new RuntimeException("Elasticsearch search failed", e);
        }

    }
}
