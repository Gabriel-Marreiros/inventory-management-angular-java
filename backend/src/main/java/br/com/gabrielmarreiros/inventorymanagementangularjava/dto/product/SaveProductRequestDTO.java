package br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.util.UUID;

public record SaveProductRequestDTO(
        String brand,
        String name,
        String sku,
        String description,
        BigDecimal price,
        int quantity,
        String image,
        String link,
        @JsonProperty("category")
        UUID categoryId,
        boolean active
) {
}
