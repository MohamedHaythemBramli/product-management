package com.product.productmanagement.service;

import com.product.productmanagement.dto.ProductDTO;
import com.product.productmanagement.entities.Product;
import com.product.productmanagement.mapper.ProductMapper;
import com.product.productmanagement.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        // Setting up the entity (Product)
        product = new Product();
        product.setName("Test Product");
        product.setDescription("Test product description");
        product.setPrice(BigDecimal.valueOf(99.99));
        product.setQuantity(10);
        product.setCategory("Electronics");
        product.setSku("Sku");

        // Setting up the DTO (ProductDTO)
        productDTO = new ProductDTO();
        productDTO.setName("Test Product DTO");
        productDTO.setDescription("Test product DTO description");
        productDTO.setPrice(BigDecimal.valueOf(99.99));
        productDTO.setQuantity(10);
        productDTO.setCategory("Electronics");
        productDTO.setSku("Sku");
    }

    @Test
    void testCreateAndGetProduct() {
        ProductDTO savedProduct = productService.createProduct(productDTO);
        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());

        List<ProductDTO> products = productService.getAllProducts();
        assertFalse(products.isEmpty());

        ProductDTO fetchedProduct = productService.getProductById(savedProduct.getId()).orElse(null);
        assertNotNull(fetchedProduct);
        assertEquals(savedProduct.getName(), fetchedProduct.getName());
    }

    @Test
    void testUpdateProduct() {
        ProductDTO savedProduct = productService.createProduct(productDTO);

        savedProduct.setName("Updated Product");
        ProductDTO updatedProduct = productService.updateProduct(savedProduct.getId(), savedProduct);

        assertEquals("Updated Product", updatedProduct.getName());
    }

    @Test
    void testDeleteProduct() {
        ProductDTO savedProduct = productService.createProduct(productDTO);
        productService.deleteProduct(savedProduct.getId());

        assertFalse(productService.getProductById(savedProduct.getId()).isPresent());
    }
}
