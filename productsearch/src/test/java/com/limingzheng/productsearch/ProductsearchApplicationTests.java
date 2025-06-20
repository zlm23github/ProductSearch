package com.limingzheng.productsearch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ProductsearchApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetAllProducts() throws Exception {
		mockMvc.perform(get("/api/products"))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetProductById() throws Exception {
		mockMvc.perform(get("/api/products/1"))
				.andExpect(status().isOk());
	}

	@Test
	public void testSearchProductsByKeyword() throws Exception {
		String keyword = "phone";
		mockMvc.perform(get("/api/products/keyword/{keyword}", keyword))
				.andExpect(status().isOk());
	}

	@Test
	public void testSearchProductsByCategory() throws Exception {
		String category = "computer";
		mockMvc.perform(get("/api/products/category/{category}", category))
				.andExpect(status().isOk());

	}

	@Test
	public void testSearchProductsByPriceRange() throws Exception {
		Double minPrice = 10.0;
		Double maxPrice = 100.0;
		mockMvc.perform(get("/api/products/price-range")
						.param("minPrice", String.valueOf(minPrice))
						.param("maxPrice", String.valueOf(maxPrice)))
				.andExpect(status().isOk());
	}

	@Test
	public void testSearchProducts() throws Exception {
		mockMvc.perform(get("/api/products/search")).andExpect(status().isOk());
	}

}
