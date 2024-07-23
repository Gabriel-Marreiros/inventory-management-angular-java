package br.com.gabrielmarreiros.inventorymanagementangularjava.repositories;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.BrandsProductsStatusReportDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.ProductsNumberRegisteredMonthReportDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.SummaryProductsCategoriesUsersActiveDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Product;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    /*findBySearchTermIgnoreCaseContaining()*/

    @Test
    void givenASearchTerm_whenFindBySearchTermIgnoreCaseContaining_thenReturnProductsWithContainsTheSearchTerm() {
        /*Arrange*/
        String searchTerm = "Marca teste 1";
        PageRequest pageRequest = PageRequest.of(0, 10);

        /*Action*/
        Page<Product> repositoryResponse = productRepository.findBySearchTermIgnoreCaseContaining(searchTerm, pageRequest);

        /*Assert*/
        assertThat(repositoryResponse.getContent())
                .hasSize(4);

    }

    /*findLastTenRegistered()*/

    @Test
    void whenFindLastTenRegistered_thenReturnLastTenRegisteredProducts() {
        /*Action*/
        List<Product> repositoryResponse = productRepository.findLastTenRegistered();

        /*Assert*/
        assertThat(repositoryResponse)
                .hasSize(10);
    }

    /*getBrandsProductsStatusReport()*/

    @Test
    void whenGetBrandsProductsStatusReport_thenReturnAListOfBrandsProductsStatusReport() {
        /*Action*/
        List<BrandsProductsStatusReportDTO> repositoryResponse = productRepository.getBrandsProductsStatusReport();

        /*Assert*/
        assertThat(repositoryResponse)
                .isNotEmpty()
                .anyMatch((p) -> p.brand().equals("Marca Teste 1") && p.activeProducts() == 2 && p.inactiveProducts() == 2)
                .anyMatch((p) -> p.brand().equals("Marca Teste 2") && p.activeProducts() == 1 && p.inactiveProducts() == 3)
                .anyMatch((p) -> p.brand().equals("Marca Teste 3") && p.activeProducts() == 3 && p.inactiveProducts() == 1);
    }

    /*getProductsNumberRegisteredMonthReport()*/

    @Test
    void whenGetProductsNumberRegisteredMonthReport_thenReturnAListOfProductsNumberRegisteredMonthReport() {
        /*Action*/
        List<ProductsNumberRegisteredMonthReportDTO> repositoryResponse = productRepository.getProductsNumberRegisteredMonthReport("2024");

        /*Assert*/
        assertThat(repositoryResponse)
                .anyMatch((p) -> p.month().equals("Janeiro") && p.registeredProductsNumber() == 4)
                .anyMatch((p) -> p.month().equals("Fevereiro") && p.registeredProductsNumber() == 4)
                .anyMatch((p) -> p.month().equals("Março") && p.registeredProductsNumber() == 4);

    }

    /*changeProductActiveStatus()*/

    @Test
    void givenAActiveProduct_whenChangeProductActiveStatus_thenInactiveTheProduct() {
        /*Arrange*/
        UUID productId = UUID.fromString("45103b60-2385-42bf-9539-e7e5adf6c27f");

        /*Action*/
        productRepository.changeProductActiveStatus(productId, false);
        Product modifiedProduct = productRepository.findById(productId).get();

        /*Assert*/
        assertThat(modifiedProduct)
                .extracting("active")
                .isEqualTo(false);
    }

    @Test
    void givenAInactiveProduct_whenChangeProductActiveStatus_thenActiveTheProduct() {
        /*Arrange*/
        UUID productId = UUID.fromString("5d5f293b-f73d-4cd3-8a1a-58e07b640cfc");

        /*Action*/
        productRepository.changeProductActiveStatus(productId, true);
        Product modifiedProduct = productRepository.findById(productId).get();

        /*Assert*/
        assertThat(modifiedProduct)
                .extracting("active")
                .isEqualTo(true);
    }

    /*getSummaryProductsCategoriesUsersActive()*/

    @Test
    void whenGetSummaryProductsCategoriesUsersActive_thenReturnSummaryProductsCategoriesUsersActive() {
        /*Action*/
        SummaryProductsCategoriesUsersActiveDTO repositoryResponse = productRepository.getSummaryProductsCategoriesUsersActive();

        /*Assert*/
        assertThat(repositoryResponse)
            .hasFieldOrPropertyWithValue("activeProducts", 6L)
            .hasFieldOrPropertyWithValue("activeCategories", 2L)
            .hasFieldOrPropertyWithValue("activeUsers", 3L);
    }
}