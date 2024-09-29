package com.product.productmanagement.mapper;

import com.product.productmanagement.dto.ProductDTO;
import com.product.productmanagement.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    // Converts Product entity to ProductDTO
    public ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setSku(product.getSku());
        return dto;
    }

    // Converts ProductDTO to Product entity
    public Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setSku(dto.getSku());
        return product;
    }

    // For updating existing entities (used in PUT and PATCH operations)
    public Product updateEntityFromDTO(ProductDTO dto, Product product) {
        if (dto.getName() != null) product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getCategory() != null) product.setCategory(dto.getCategory());
        if (dto.getSku() != null) product.setSku(dto.getSku());
        return product;
    }
}

