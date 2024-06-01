package br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.category.CategoryResponseDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Category;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String brand,
        String name,
        String sku,
        String description,
        BigDecimal price,
        int quantity,
        String image,
        String link,
        boolean active,
        CategoryResponseDTO category
) {
}
