package br.com.gabrielmarreiros.inventorymanagementangularjava.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public record UserUpdateRequestDTO(
    String name,
    String email,
    String phoneNumber,
    LocalDate dateBirth,
    @JsonProperty("role")
    UUID roleId
) {}
