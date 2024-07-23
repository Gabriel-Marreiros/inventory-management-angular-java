package br.com.gabrielmarreiros.inventorymanagementangularjava.services;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.BrandsProductsStatusReportDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.ProductUpdateRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.ProductsNumberRegisteredMonthReportDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.SummaryProductsCategoriesUsersActiveDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions.InvalidSortException;
import br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions.ProductNotFoundException;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Product;
import br.com.gabrielmarreiros.inventorymanagementangularjava.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mapping.PropertyReferenceException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void initMocks(){
        openMocks(this);
    }

    /*getAllProducts()*/
    @Test
    void whenGetAllProducts_thenReturnAListOfProducts() {
        /*Arrange*/
        Product productMock = mock(Product.class);
        List<Product> productListMock = new ArrayList<>();
        productListMock.add(productMock);

        when(productRepository.findAll()).thenReturn(productListMock);

        /*Action*/
        List<Product> serviceResponse = productService.getAllProducts();

        /*Assert*/
        assertThat(serviceResponse)
                .isNotEmpty()
                .isEqualTo(productListMock);

    }

  /*getProductsPaginated()*/

    @Test
    void givenAValidSort_whenGetProductsPaginated_thenReturnAPageOfProducts() {
        /*Arrange*/
        Product productMock = mock(Product.class);
        List<Product> productList = new ArrayList<>();
        productList.add(productMock);
        PageRequest pageRequestMock = mock(PageRequest.class);
        Page<Product> productPage = new PageImpl<>(productList, pageRequestMock, 1);
        when(productRepository.findAll(pageRequestMock)).thenReturn(productPage);

        /*Action*/
        Page<Product> serviceResponse = productService.getProductsPaginated(pageRequestMock);

        /*Assert*/
        assertThat(serviceResponse)
                .isEqualTo(productPage);

    }

    @Test
    void givenAInvalidSort_whenGetProductsPaginated_thenThrowsInvalidSortException() {
        /*Arrange*/
        PageRequest pageRequestMock = mock(PageRequest.class);
        when(productRepository.findAll(pageRequestMock)).thenThrow(PropertyReferenceException.class);

        /*Action*/
        Exception serviceResponse = catchException(() -> productService.getProductsPaginated(pageRequestMock));

        /*Assert*/
        assertThat(serviceResponse)
            .isInstanceOf(InvalidSortException.class);

    }

  /*getProductsBySearchFilterPaginated()*/

    @Test
    void givenAValidSort_whenGetProductsBySearchFilterPaginated_thenReturnAPageOfProducts() {
        /*Arrange*/
        String searchFilter = "Search Filter";
        Product productMock = mock(Product.class);
        List<Product> productList = new ArrayList<>();
        productList.add(productMock);
        PageRequest pageRequestMock = mock(PageRequest.class);
        Page<Product> productPage = new PageImpl<>(productList, pageRequestMock, 1);
        when(productRepository.findBySearchTermIgnoreCaseContaining(searchFilter, pageRequestMock)).thenReturn(productPage);

        /*Action*/
        Page<Product> serviceResponse = productService.getProductsBySearchFilterPaginated(searchFilter, pageRequestMock);

        /*Assert*/
        assertThat(serviceResponse)
                .isEqualTo(productPage);

    }

    @Test
    void givenAInvalidSort_whenGetProductsBySearchFilterPaginated_thenThrowsInvalidSortException() {
        /*Arrange*/
        String invalidSearchFilter = "Invalid Search Filter";
        PageRequest pageRequestMock = mock(PageRequest.class);
        when(productRepository.findBySearchTermIgnoreCaseContaining(invalidSearchFilter, pageRequestMock)).thenThrow(PropertyReferenceException.class);

        /*Action*/
        Exception serviceResponse = catchException(() -> productService.getProductsBySearchFilterPaginated(invalidSearchFilter, pageRequestMock));

        /*Assert*/
        assertThat(serviceResponse)
                .isInstanceOf(InvalidSortException.class);

    }

  /*getLastTenRegistered()*/

    @Test
    void whenGetLastTenRegistered_thenReturnAListWithLastTenRegistered() {
        /*Arrange*/
        Product productMock = mock(Product.class);
        List<Product> productList = new ArrayList<>();
        productList.add(productMock);
        when(productRepository.findLastTenRegistered()).thenReturn(productList);

        /*Action*/
        List<Product> serviceResponse = productService.getLastTenRegistered();

        /*Assert*/
        assertThat(serviceResponse)
                .isEqualTo(productList);
    }

  /*getProductById()*/

    @Test
    void givenAValidId_whenGetProductById_thenReturnAProduct() {
        /*Arrange*/
        UUID productId = UUID.randomUUID();
        Product productMock = mock(Product.class);
        when(productRepository.findById(productId)).thenReturn(Optional.of(productMock));

        /*Action*/
        Product serviceResponse = productService.getProductById(productId);

        /*Assert*/
        assertThat(serviceResponse)
                .isInstanceOf(Product.class)
                .isEqualTo(productMock);

    }

    @Test
    void givenAInvalidId_whenGetProductById_thenThrowsProductNotFoundException() {
        /*Arrange*/
        UUID productId = UUID.randomUUID();
        when(productRepository.findById(productId)).thenReturn(Optional.ofNullable(null));

        /*Action*/
        Exception serviceResponse = catchException(() -> productService.getProductById(productId));

        /*Assert*/
        assertThat(serviceResponse)
                .isInstanceOf(ProductNotFoundException.class);

    }

  /*saveProduct()*/

    @Test
    void givenAProduct_whenSaveProduct_thenReturnProduct() {
        /*Arrange*/
        Product productMock = mock(Product.class);
        doNothing().when(productMock).generateSearchTerm();
        when(productRepository.save(productMock)).thenReturn(productMock);

        /*Action*/
        Product serviceResponse = productService.saveProduct(productMock);

        /*Assert*/
        assertThat(serviceResponse)
                .isEqualTo(productMock)
                .hasFieldOrPropertyWithValue("createdAt", isA(LocalDateTime.class));

    }

  /*updateProduct()*/

    @Test
    void givenAUnknownProductId_whenUpdateProduct_thenThrowsProductNotFoundException() {
        /*Arrange*/
        UUID productId = UUID.randomUUID();
        ProductUpdateRequestDTO productUpdateRequestMock = mock(ProductUpdateRequestDTO.class);
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        /*Action*/
        Exception serviceResponse = catchException(() -> productService.updateProduct(productUpdateRequestMock, productId));

        /*Assert*/
        assertThat(serviceResponse)
                .isInstanceOf(ProductNotFoundException.class);

    }

  /*getBrandsProductsStatusReport()*/

    @Test
    void whenGetBrandsProductsStatusReport_thenReturnAListOfBrandsProductsStatusReport() {
        /*Arrange*/
        BrandsProductsStatusReportDTO brandsProductsStatusReportMock = mock(BrandsProductsStatusReportDTO.class);
        List<BrandsProductsStatusReportDTO> brandsProductsStatusReportList = new ArrayList<>();
        brandsProductsStatusReportList.add(brandsProductsStatusReportMock);

        when(productRepository.getBrandsProductsStatusReport()).thenReturn(brandsProductsStatusReportList);

        /*Action*/
        List<BrandsProductsStatusReportDTO> serviceResponse = productService.getBrandsProductsStatusReport();

        /*Assert*/
        assertThat(serviceResponse)
                .isNotEmpty()
                .isEqualTo(brandsProductsStatusReportList);

    }

  /*getProductsNumberRegisteredMonthReport()*/

    @Test
    void givenAYearFilter_whenGetProductsNumberRegisteredMonthReport_then() {
        /*Arrange*/
        String year = null;
        ProductsNumberRegisteredMonthReportDTO productsNumberRegisteredMonthReportMock = mock(ProductsNumberRegisteredMonthReportDTO.class);
        List<ProductsNumberRegisteredMonthReportDTO> productsNumberRegisteredMonthReportList = new ArrayList<>();
        productsNumberRegisteredMonthReportList.add(productsNumberRegisteredMonthReportMock);

        when(productRepository.getProductsNumberRegisteredMonthReport(nullable(String.class))).thenReturn(productsNumberRegisteredMonthReportList);

        /*Action*/
        List<ProductsNumberRegisteredMonthReportDTO> serviceResponse = productService.getProductsNumberRegisteredMonthReport(year);

        /*Assert*/
        assertThat(serviceResponse)
                .isNotEmpty()
                .isEqualTo(productsNumberRegisteredMonthReportList);

    }

//    @Test
//    void givenANullYearFilter_whenGetProductsNumberRegisteredMonthReport_then() {
//        /*Arrange*/
//        String year = null;
//        ProductsNumberRegisteredMonthReportDTO productsNumberRegisteredMonthReportMock = mock(ProductsNumberRegisteredMonthReportDTO.class);
//        List<ProductsNumberRegisteredMonthReportDTO> productsNumberRegisteredMonthReportList = new ArrayList<>();
//        productsNumberRegisteredMonthReportList.add(productsNumberRegisteredMonthReportMock);
//
//        when(productRepository.getProductsNumberRegisteredMonthReport(nullable(String.class))).thenReturn(productsNumberRegisteredMonthReportList);
//
//        /*Action*/
//        List<ProductsNumberRegisteredMonthReportDTO> serviceResponse = productService.getProductsNumberRegisteredMonthReport(year);
//
//        /*Assert*/
//        assertThat(serviceResponse)
//                .isNotEmpty()
//                .isEqualTo(productsNumberRegisteredMonthReportList);
//
//        assertThat(year)
//                .isEqualTo(String.valueOf(LocalDateTime.now().getYear()));
//
//    }


  /*getSummaryProductsCategoriesUsersActive()*/

    @Test
    void whenGetSummaryProductsCategoriesUsersActive_thenReturnASummaryProductsCategoriesUsersActiveDTO() {
        /*Arrange*/
        SummaryProductsCategoriesUsersActiveDTO summaryProductsCategoriesUsersActiveMock = mock(SummaryProductsCategoriesUsersActiveDTO.class);
        when(productRepository.getSummaryProductsCategoriesUsersActive()).thenReturn(summaryProductsCategoriesUsersActiveMock);

        /*Action*/
        SummaryProductsCategoriesUsersActiveDTO serviceResponse = productService.getSummaryProductsCategoriesUsersActive();

        /*Assert*/
        assertThat(serviceResponse)
                .isInstanceOf(SummaryProductsCategoriesUsersActiveDTO.class)
                .isEqualTo(summaryProductsCategoriesUsersActiveMock);

    }
}