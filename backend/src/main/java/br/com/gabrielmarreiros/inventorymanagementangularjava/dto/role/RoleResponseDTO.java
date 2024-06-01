package br.com.gabrielmarreiros.inventorymanagementangularjava.dto.role;

import java.util.UUID;

public record RoleResponseDTO(
        UUID id,
        String name
) {
}
