package br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product;

public record ProductsNumberRegisteredMonthReportDTO(
        String month,
        long registeredProductsNumber
) {
}

//    relatorio de numero produtos registrados por mês