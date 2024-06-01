package br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product;

public record SummaryProductsCategoriesUsersActiveDTO(
        Long activeProducts,
        Long activeCategories,
        Long activeUsers
) {}
