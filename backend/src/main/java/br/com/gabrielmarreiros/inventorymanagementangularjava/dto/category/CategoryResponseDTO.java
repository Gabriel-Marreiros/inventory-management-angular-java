package br.com.gabrielmarreiros.inventorymanagementangularjava.dto.category;

import java.util.UUID;

public record CategoryResponseDTO(
        UUID id,
        String name,
        boolean active
) {
}
