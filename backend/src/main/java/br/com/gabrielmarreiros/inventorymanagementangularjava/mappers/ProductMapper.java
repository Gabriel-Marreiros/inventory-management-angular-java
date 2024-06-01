package br.com.gabrielmarreiros.inventorymanagementangularjava.mappers;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.ProductResponseDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.SaveProductRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Category;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Product;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    public ProductMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public Product toEntity(SaveProductRequestDTO saveProductRequest){
        Category category = new Category(saveProductRequest.categoryId());

        Product productEntity = new Product();
        productEntity.setBrand(saveProductRequest.brand());
        productEntity.setName(saveProductRequest.name());
        productEntity.setSku(saveProductRequest.sku());
        productEntity.setDescription(saveProductRequest.description());
        productEntity.setPrice(saveProductRequest.price());
        productEntity.setQuantity(saveProductRequest.quantity());
        productEntity.setImage(saveProductRequest.image());
        productEntity.setLink(saveProductRequest.link());
        productEntity.setCategory(category);
        productEntity.setActive(saveProductRequest.active());

        return productEntity;
    }

    public ProductResponseDTO toResponseDTO(Product productEntity){
        return new ProductResponseDTO(
                productEntity.getId(),
                productEntity.getBrand(),
                productEntity.getName(),
                productEntity.getSku(),
                productEntity.getDescription(),
                productEntity.getPrice(),
                productEntity.getQuantity(),
                productEntity.getImage(),
                productEntity.getLink(),
                productEntity.getActive(),
                this.categoryMapper.toResponseDTO(productEntity.getCategory())
        );
    }

    public List<ProductResponseDTO> toResponseListDTO(List<Product> productsEntitiesList){
        return productsEntitiesList.stream().map(this::toResponseDTO).toList();
    }

    public Page<ProductResponseDTO> toResponsePageDTO(Page<Product> productsPageEnity){
        return productsPageEnity.map(this::toResponseDTO);
    }
}
