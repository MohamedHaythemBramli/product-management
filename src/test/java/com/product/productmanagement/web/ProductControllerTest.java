package com.product.productmanagement.web;

import com.product.productmanagement.dto.ProductDTO;
import com.product.productmanagement.exception.ResourceNotFoundException;
import com.product.productmanagement.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Test Product");
        productDTO.setDescription("Test description");
        productDTO.setPrice(BigDecimal.valueOf(99.99));
        productDTO.setQuantity(10);
        productDTO.setCategory("Electronics");
    }

    @Test
    void testGetAllProducts() {
        when(productService.getAllProducts()).thenReturn(Arrays.asList(productDTO));

        ResponseEntity<List<ProductDTO>> response = productController.getAllProducts();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById() {
        when(productService.getProductById(anyLong())).thenReturn(Optional.of(productDTO));

        ResponseEntity<ProductDTO> response = productController.getProductById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(productDTO.getName(), response.getBody().getName());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testGetProductByIdNotFound() {
        when(productService.getProductById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productController.getProductById(1L));
    }

    @Test
    void testCreateProduct() {
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(productDTO);

        ResponseEntity<ProductDTO> response = productController.createProduct(productDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(productDTO.getName(), response.getBody().getName());
        verify(productService, times(1)).createProduct(any(ProductDTO.class));
    }

    @Test
    void testUpdateProduct() {
        when(productService.updateProduct(anyLong(), any(ProductDTO.class))).thenReturn(productDTO);

        ResponseEntity<ProductDTO> response = productController.updateProduct(1L, productDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(productDTO.getName(), response.getBody().getName());
        verify(productService, times(1)).updateProduct(anyLong(), any(ProductDTO.class));
    }

    @Test
    void testPatchProduct() {
        when(productService.patchProduct(anyLong(), any(ProductDTO.class))).thenReturn(productDTO);

        ResponseEntity<ProductDTO> response = productController.patchProduct(1L, productDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(productDTO.getName(), response.getBody().getName());
        verify(productService, times(1)).patchProduct(anyLong(), any(ProductDTO.class));
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productService).deleteProduct(anyLong());

        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deleteProduct(anyLong());
    }
}