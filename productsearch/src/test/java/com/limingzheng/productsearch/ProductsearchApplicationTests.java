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

}
