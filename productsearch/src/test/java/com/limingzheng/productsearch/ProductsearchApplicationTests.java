package com.limingzheng.productsearch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class ProductsearchApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testSearchProductsWithPagination() throws Exception {
		mockMvc.perform(get("/api/products/search")
						.param("page", "0")       // Request page 0
						.param("size", "5")       // Request 5 items per page
						.param("sort", "price,asc") // Sort by price ascending
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.content").isArray())           // Assert that the content is an array
				.andExpect(jsonPath("$.totalPages").isNumber())     // Assert that totalPages is a number
				.andExpect(jsonPath("$.totalElements").isNumber())  // Assert that totalElements is a number
				.andExpect(jsonPath("$.size").value(5))             // Assert that the page size is 5
				.andExpect(jsonPath("$.number").value(0));          // Assert that the current page number is 0
	}





}
