package com.product.productmanagement.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.productmanagement.dto.ProductDTO;
import com.product.productmanagement.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        // Clear the repository before each test
        productRepository.deleteAll();

        // Create a ProductDTO object with required fields
        productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setDescription("Test description");
        productDTO.setPrice(BigDecimal.valueOf(99.99));
        productDTO.setQuantity(10);  // Set quantity field
        productDTO.setCategory("Electronics");  // Set category field
        productDTO.setSku("Sku12345");  // Set SKU field
    }

    @Test
    void testCreateProduct() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())  // Expecting 201 Created status
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.description").value("Test description"))
                .andExpect(jsonPath("$.price").value(99.99))
                .andExpect(jsonPath("$.quantity").value(10))  // Verifying quantity value
                .andExpect(jsonPath("$.category").value("Electronics"))  // Verifying category value
                .andExpect(jsonPath("$.sku").value("Sku12345"));  // Verifying SKU value
    }

    @Test
    void testGetAllProducts() throws Exception {
        // First, create a product
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated());

        // Then, retrieve all products
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())  // Expecting 200 OK status
                .andExpect(jsonPath("$[0].name").value("Test Product"))
                .andExpect(jsonPath("$[0].description").value("Test description"))
                .andExpect(jsonPath("$[0].price").value(99.99))
                .andExpect(jsonPath("$[0].quantity").value(10))  // Verifying quantity value
                .andExpect(jsonPath("$[0].category").value("Electronics"))  // Verifying category value
                .andExpect(jsonPath("$[0].sku").value("Sku12345"));
    }

    @Test
    void testGetProductById() throws Exception {
        // First, create a product and get its response content as a string
        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Convert the response into ProductDTO object to retrieve its ID
        ProductDTO createdProduct = objectMapper.readValue(response, ProductDTO.class);

        // Fetch product by its ID and validate the response
        mockMvc.perform(get("/api/products/{id}", createdProduct.getId()))
                .andExpect(status().isOk())  // Expecting 200 OK status
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.description").value("Test description"))
                .andExpect(jsonPath("$.price").value(99.99))
                .andExpect(jsonPath("$.quantity").value(10))  // Verifying quantity value
                .andExpect(jsonPath("$.category").value("Electronics"))  // Verifying category value
                .andExpect(jsonPath("$.sku").value("Sku12345"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        // First, create a product
        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Convert the response into ProductDTO object to retrieve its ID
        ProductDTO createdProduct = objectMapper.readValue(response, ProductDTO.class);
        createdProduct.setName("Updated Product");  // Update the name of the product
        createdProduct.setQuantity(15);  // Update the quantity of the product
        createdProduct.setCategory("Home Appliances");  // Update the category of the product

        // Update the product and verify the name, quantity, and category changes
        mockMvc.perform(put("/api/products/{id}", createdProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createdProduct)))
                .andExpect(status().isOk())  // Expecting 200 OK status
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.quantity").value(15))  // Verifying updated quantity value
                .andExpect(jsonPath("$.category").value("Home Appliances"))  // Verifying updated category value
                .andExpect(jsonPath("$.sku").value("Sku12345"));  // Verifying SKU remains the same
    }

    @Test
    void testDeleteProduct() throws Exception {
        // First, create a product
        String response = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Convert the response into ProductDTO object to retrieve its ID
        ProductDTO createdProduct = objectMapper.readValue(response, ProductDTO.class);

        // Delete the product by ID and verify it's deleted
        mockMvc.perform(delete("/api/products/{id}", createdProduct.getId()))
                .andExpect(status().isNoContent());  // Expecting 204 No Content status

        // Try to fetch the deleted product and expect a 404 Not Found status
        mockMvc.perform(get("/api/products/{id}", createdProduct.getId()))
                .andExpect(status().isNotFound());
    }
}
