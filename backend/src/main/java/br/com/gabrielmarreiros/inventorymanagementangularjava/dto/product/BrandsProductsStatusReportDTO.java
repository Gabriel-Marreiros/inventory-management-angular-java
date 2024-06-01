package br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product;

public record BrandsProductsStatusReportDTO(
        String brand,
        long activeProducts,
        long inactiveProducts
) {
}
