package br.com.gabrielmarreiros.inventorymanagementangularjava.mappers;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.category.CategoryResponseDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.category.SaveCategoryRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    public Category toEntity(SaveCategoryRequestDTO categorySaveRequest){
        Category category = new Category();
        category.setName(categorySaveRequest.name());

        return category;
    }

    public CategoryResponseDTO toResponseDTO(Category categoryEntity){
        return new CategoryResponseDTO(
            categoryEntity.getId(),
            categoryEntity.getName(),
            categoryEntity.getActive()
        );
    }

    public List<CategoryResponseDTO> toResponseListDTO(List<Category> categoriesEntityList){
        return categoriesEntityList.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Page<CategoryResponseDTO> toResponsePageDTO(Page<Category> categoriesEntityPage) {
        return categoriesEntityPage.map(this::toResponseDTO);
    }
}
