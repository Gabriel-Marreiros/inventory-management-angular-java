package br.com.gabrielmarreiros.inventorymanagementangularjava.dto.user;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public record SaveUserRequestDTO(
        String name,
        String email,
        String password,
        String phoneNumber,
        LocalDate dateBirth,
        UUID role
) {
}
