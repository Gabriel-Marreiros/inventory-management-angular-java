package br.com.gabrielmarreiros.inventorymanagementangularjava.services;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.BrandsProductsStatusReportDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.ProductUpdateRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.ProductsNumberRegisteredMonthReportDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.SummaryProductsCategoriesUsersActiveDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions.InvalidSortException;
import br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions.ProductNotFoundException;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Category;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Product;
import br.com.gabrielmarreiros.inventorymanagementangularjava.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return this.productRepository.findAll();
    }

    public Page<Product> getProductsPaginated(PageRequest pageRequest){
        try{
            return this.productRepository.findAll(pageRequest);
        }
        catch(PropertyReferenceException e){
            throw new InvalidSortException();
        }
    }

    public Page<Product> getProductsBySearchFilterPaginated(String searchFilter, PageRequest pageRequest){
        try{
            return this.productRepository.findBySearchTermIgnoreCaseContaining(searchFilter, pageRequest);
        }
        catch(PropertyReferenceException e){
            throw new InvalidSortException();
        }
    }

    public List<Product> getLastTenRegistered(){
        return this.productRepository.findLastTenRegistered();
    }

    public Product getProductById(UUID id){
        return this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    @Transactional
    public Product saveProduct(Product product){
        product.setCreatedAt(LocalDateTime.now());
        product.generateSearchTerm();

        return this.productRepository.save(product);
    }
    @Transactional
    public Product updateProduct(ProductUpdateRequestDTO productUpdate, UUID productId) {
        Product productEntity = this.productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        Category categoryUpdate = new Category(productUpdate.category());

        productEntity.setBrand(productUpdate.brand());
        productEntity.setName(productUpdate.name());
        productEntity.setCategory(categoryUpdate);
        productEntity.setSku(productUpdate.sku());
        productEntity.setPrice(productUpdate.price());
        productEntity.setQuantity(productUpdate.quantity());
        productEntity.setLink(productUpdate.link());
        productEntity.setImage(productUpdate.image());
        productEntity.setDescription(productUpdate.description());

        productEntity.generateSearchTerm();

        Product updatedProduct = this.productRepository.save(productEntity);

        return updatedProduct;
    }

    public List<BrandsProductsStatusReportDTO> getBrandsProductsStatusReport(){
        return this.productRepository.getBrandsProductsStatusReport();
    }

    public List<ProductsNumberRegisteredMonthReportDTO> getProductsNumberRegisteredMonthReport(String year){
        if(year == null || year.isEmpty()){
            year = String.valueOf(LocalDate.now().getYear());
        }
        return this.productRepository.getProductsNumberRegisteredMonthReport(year);
    }

    @Transactional
    public void changeProductActiveStatus(UUID id, boolean newActiveStatus) {
        Integer modifiedRows = this.productRepository.changeProductActiveStatus(id, newActiveStatus);
    }

    public SummaryProductsCategoriesUsersActiveDTO getSummaryProductsCategoriesUsersActive(){
        return this.productRepository.getSummaryProductsCategoriesUsersActive();
    }
}
