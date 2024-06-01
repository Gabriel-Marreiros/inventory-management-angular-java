package br.com.gabrielmarreiros.inventorymanagementangularjava.controllers;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.*;
import br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions.InvalidSortException;
import br.com.gabrielmarreiros.inventorymanagementangularjava.mappers.ProductMapper;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Product;
import br.com.gabrielmarreiros.inventorymanagementangularjava.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        List<Product> productsEntitiesList = this.productService.getAllProducts();

        List<ProductResponseDTO> productsResponseList = this.productMapper.toResponseListDTO(productsEntitiesList);

        return ResponseEntity.ok(productsResponseList);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<ProductResponseDTO>> getProductsPaginated(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false, defaultValue = "brand", name = "sortBy") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder
    ){
        Sort sort = Sort.by(sortField);

        switch (sortOrder){
            case "asc": sort = sort.ascending(); break;
            case "desc": sort = sort.descending(); break;
            default: throw new InvalidSortException();
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Product> productsEntityPage = this.productService.getProductsPaginated(pageRequest);

        Page<ProductResponseDTO> productsResponsePage = this.productMapper.toResponsePageDTO(productsEntityPage);

        return ResponseEntity.ok(productsResponsePage);

    }

    @GetMapping("/last-ten-registered")
    public ResponseEntity<List<ProductResponseDTO>> getLastTenRegistered(){
        List<Product> productsEntityList = this.productService.getLastTenRegistered();

        List<ProductResponseDTO> productsListResponse = this.productMapper.toResponseListDTO(productsEntityList);

        return ResponseEntity.ok(productsListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable("id") UUID id){
        Product productEntity = this.productService.getProductById(id);

        ProductResponseDTO productResponse = this.productMapper.toResponseDTO(productEntity);

        return ResponseEntity.ok(productResponse);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> saveProduct(@RequestBody SaveProductRequestDTO saveProductRequest){
        Product productEntity = this.productMapper.toEntity(saveProductRequest);

        Product savedProduct = this.productService.saveProduct(productEntity);

        ProductResponseDTO productResponse = this.productMapper.toResponseDTO(savedProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@RequestBody ProductUpdateRequestDTO productUpdate, @PathVariable("id") UUID productId){
        Product updatedProductEntity = this.productService.updateProduct(productUpdate, productId);

        ProductResponseDTO updatedProductResponse = this.productMapper.toResponseDTO(updatedProductEntity);

        return ResponseEntity.ok(updatedProductResponse);
    }

    @GetMapping("/brands-products-status-report")
    public ResponseEntity<List<BrandsProductsStatusReportDTO>> getBrandsProductsStatusReport(){
        List<BrandsProductsStatusReportDTO> brandsProductsStatusReport = this.productService.getBrandsProductsStatusReport();

        return ResponseEntity.ok(brandsProductsStatusReport);
    }

    @GetMapping("/products-number-registered-month-report")
    public ResponseEntity<List<ProductsNumberRegisteredMonthReportDTO>> getProductsNumberRegisteredMonthReport(@RequestParam("year") String year){
        List<ProductsNumberRegisteredMonthReportDTO> productsNumberRegisteredMonthReport = this.productService.getProductsNumberRegisteredMonthReport(year);

        return ResponseEntity.ok(productsNumberRegisteredMonthReport);
    }

    @DeleteMapping(path = "{id}/change-active-status", params = {"active"})
    public ResponseEntity changeProductActiveStatus(@PathVariable("id") UUID id, @RequestParam("active") boolean newActiveStatus){
        this.productService.changeProductActiveStatus(id, newActiveStatus);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary-products-categories-users-active")
    public ResponseEntity<SummaryProductsCategoriesUsersActiveDTO> getSummaryProductsCategoriesUsersActive(){
        SummaryProductsCategoriesUsersActiveDTO summaryProductsCategoriesUsersActive = this.productService.getSummaryProductsCategoriesUsersActive();

        return ResponseEntity.ok(summaryProductsCategoriesUsersActive);
    }

    @GetMapping("/search-filter/paginated")
    public ResponseEntity<Page<ProductResponseDTO>> getProductsBySearchTerm(
            @RequestParam String searchFilter,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false, defaultValue = "brand", name = "sortBy") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder
    ){
        Sort sort = Sort.by(sortField);

        switch (sortOrder){
            case "asc": sort.ascending(); break;
            case "desc": sort.descending(); break;
            default: throw new InvalidSortException();
        }

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Product> productsPage = this.productService.getProductsBySearchFilterPaginated(searchFilter, pageRequest);

        Page<ProductResponseDTO> productsPageResponse = this.productMapper.toResponsePageDTO(productsPage);

        return ResponseEntity.ok(productsPageResponse);

    }

}
