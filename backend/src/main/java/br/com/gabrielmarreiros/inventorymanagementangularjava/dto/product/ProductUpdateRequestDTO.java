package br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductUpdateRequestDTO(
        String brand,
        String name,
        UUID category,
        String sku,
        BigDecimal price,
        int quantity,
        String link,
        String description
) {
}
