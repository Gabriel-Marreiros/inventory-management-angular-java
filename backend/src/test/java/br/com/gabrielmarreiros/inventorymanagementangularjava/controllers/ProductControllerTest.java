package br.com.gabrielmarreiros.inventorymanagementangularjava.controllers;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.*;
import br.com.gabrielmarreiros.inventorymanagementangularjava.infra.JwtFilter;
import br.com.gabrielmarreiros.inventorymanagementangularjava.mappers.ProductMapper;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Product;
import br.com.gabrielmarreiros.inventorymanagementangularjava.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class)})
class ProductControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService productService;
    @MockBean
    private ProductMapper productMapper;

    /*getAllProducts()*/

    @Test
    void whenGetAllProducts_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        List<Product> productsList = new ArrayList<>();
        when(productService.getAllProducts()).thenReturn(productsList);

        /*Action*/
        var response = mvc.perform(
            get("/products")
        );

        /*Assert*/
        response
            .andExpect(status().isOk());
    }

    /*getProductsPaginated()*/

    @Test
    void whenGetProductsPaginated_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        Page<Product> productsPage = mock(PageImpl.class);
        when(productService.getProductsPaginated(any(PageRequest.class))).thenReturn(productsPage);

        /*Action*/
        var response = mvc.perform(
            get("/products/paginated")
                .queryParam("page", "0")
                .queryParam("size", "1")
                .queryParam("sortBy", "brand")
                .queryParam("sortOrder", "desc")
        );

        /*Assert*/
        response
                .andExpect(status().isOk());
    }

    @Test
    void givenAInvalidSort_whenGetProductsPaginated_thenReturn400BadRequest() throws Exception {
        /*Arrange*/
        Page<Product> productsPage = mock(PageImpl.class);
        when(productService.getProductsPaginated(any(PageRequest.class))).thenReturn(productsPage);

        /*Action*/
        var response = mvc.perform(
            get("/products/paginated")
                .queryParam("page", "0")
                .queryParam("size", "1")
                .queryParam("sortBy", "brand")
                .queryParam("sortOrder", "invalid sort")
        );

        /*Assert*/
        response
            .andExpect(status().isBadRequest());
    }

    /*getLastTenRegistered()*/

    @Test
    void whenGetLastTenRegistered_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        List<Product> productsList = new ArrayList<>();
        when(productService.getLastTenRegistered()).thenReturn(productsList);

        /*Action*/
        var response = mvc.perform(
                get("/products/last-ten-registered")
        );

        /*Assert*/
        response
            .andExpect(status().isOk());
    }

    /*getProductById()*/

    @Test
    void whenGetProductById_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        Product productMock = mock(Product.class);
        UUID productId = UUID.randomUUID();
        when(productService.getProductById(productId)).thenReturn(productMock);

        /*Action*/
        var response = mvc.perform(
            get("/products", productId)
        );

        /*Assert*/
        response
            .andExpect(status().isOk());
    }

    /*saveProduct()*/

    @Test
    void whenSaveProduct_thenReturnStatus201Created() throws Exception {
        /*Arrange*/
        SaveProductRequestDTO saveProductRequestMock = mock(SaveProductRequestDTO.class);
        Product productMock = mock(Product.class);
        when(productService.saveProduct(any(Product.class))).thenReturn(productMock);

        /*Action*/
        var response = mvc.perform(
            post("/products")
                .content(objectMapper.writeValueAsString(saveProductRequestMock))
                .contentType(MediaType.APPLICATION_JSON)
        );

        /*Assert*/
        response
                .andExpect(status().isCreated());
    }

    /*updateProduct()*/

    @Test
    void whenUpdateProduct_thenReturnStatus201Created() throws Exception {
        /*Arrange*/
        ProductUpdateRequestDTO updateProductRequestMock = mock(ProductUpdateRequestDTO.class);
        Product productMock = mock(Product.class);
        UUID productId = UUID.randomUUID();
        when(productService.updateProduct(updateProductRequestMock, productId)).thenReturn(productMock);

        /*Action*/
        var response = mvc.perform(
                post("/products")
                        .content(objectMapper.writeValueAsString(updateProductRequestMock))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        /*Assert*/
        response
                .andExpect(status().isCreated());
    }

    /*getBrandsProductsStatusReport()*/

    @Test
    void whenGetBrandsProductsStatusReport_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        List<BrandsProductsStatusReportDTO> brandsProductsStatusReportList = new ArrayList<>();
        when(productService.getBrandsProductsStatusReport()).thenReturn(brandsProductsStatusReportList);

        /*Action*/
        var response = mvc.perform(
            get("/products/brands-products-status-report")
        );

        /*Assert*/
        response
                .andExpect(status().isOk());
    }

    /*getProductsNumberRegisteredMonthReport()*/

    @Test
    void whenGetProductsNumberRegisteredMonthReport_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        List<ProductsNumberRegisteredMonthReportDTO> productsNumberRegisteredMonthReport = new ArrayList<>();
        when(productService.getProductsNumberRegisteredMonthReport(anyString())).thenReturn(productsNumberRegisteredMonthReport);

        /*Action*/
        var response = mvc.perform(
            get("/products/products-number-registered-month-report")
                .queryParam("year", "2024")
        );

        /*Assert*/
        response
                .andExpect(status().isOk());
    }

    /*changeProductActiveStatus()*/

    @Test
    void whenChangeProductActiveStatus_thenReturnStatus204NoContent() throws Exception {
        /*Arrange*/
        UUID productId = UUID.randomUUID();
        doNothing().when(productService).changeProductActiveStatus(productId, false);

        /*Action*/
        var response = mvc.perform(
                delete("/products/" + productId +"/change-active-status")
                    .queryParam("active", "false")
        );

        /*Assert*/
        response
                .andExpect(status().isNoContent());
    }

    /*getSummaryProductsCategoriesUsersActive()*/

    @Test
    void whenGetSummaryProductsCategoriesUsersActive_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        SummaryProductsCategoriesUsersActiveDTO summaryProductsCategoriesUsersActiveMock = mock(SummaryProductsCategoriesUsersActiveDTO.class);
        when(productService.getSummaryProductsCategoriesUsersActive()).thenReturn(summaryProductsCategoriesUsersActiveMock);

        /*Action*/
        var response = mvc.perform(
                get("/products/summary-products-categories-users-active")
        );

        /*Assert*/
        response
                .andExpect(status().isOk());
    }

    /*getProductsBySearchTerm()*/

    @Test
    void whenGetProductsBySearchTerm_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        Page<Product> productsPage = mock(PageImpl.class);
        when(productService.getProductsBySearchFilterPaginated(anyString(), any(PageRequest.class))).thenReturn(productsPage);

        /*Action*/
        var response = mvc.perform(
            get("/products/search-filter/paginated")
                .queryParam("searchFilter", "filter")
                .queryParam("page", "0")
                .queryParam("size", "1")
                .queryParam("sortBy", "brand")
                .queryParam("sortOrder", "desc")
        );

        /*Assert*/
        response
            .andExpect(status().isOk());
    }

    @Test
    void givenAInvalidSort_whenGetProductsBySearchTerm_thenReturn400BadRequest() throws Exception {
        /*Arrange*/
        Page<Product> productsPage = mock(PageImpl.class);
        when(productService.getProductsBySearchFilterPaginated(anyString(), any(PageRequest.class))).thenReturn(productsPage);

        /*Action*/
        var response = mvc.perform(
            get("/products/search-filter/paginated")
                .queryParam("searchFilter", "filter")
                .queryParam("page", "0")
                .queryParam("size", "1")
                .queryParam("sortBy", "brand")
                .queryParam("sortOrder", "invalid sort")
        );

        /*Assert*/
        response
            .andExpect(status().isBadRequest());
    }
}