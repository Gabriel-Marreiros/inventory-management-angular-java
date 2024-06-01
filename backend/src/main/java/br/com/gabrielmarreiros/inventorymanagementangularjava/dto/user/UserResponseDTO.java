package br.com.gabrielmarreiros.inventorymanagementangularjava.dto.user;

import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email,
        String phoneNumber,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dateBirth,
        boolean active,
        Role role
) {
}
