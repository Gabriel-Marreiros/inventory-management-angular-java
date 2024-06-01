package br.com.gabrielmarreiros.inventorymanagementangularjava.services;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.category.UpdateCategoryRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.CategoryProductCountDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions.CategoryNotFoundException;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Category;
import br.com.gabrielmarreiros.inventorymanagementangularjava.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories(){
        return this.categoryRepository.findAll();
    }

    public Category getCategoryById(UUID id){
        return this.categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
    }

    @Transactional
    public Category saveCategory(Category category){
        category.setActive(true);
        category.setCreatedAt(LocalDateTime.now());
        return this.categoryRepository.save(category);
    }


    @Transactional
    public Category updateCategory(UUID id, UpdateCategoryRequestDTO updateCategoryRequest){
        Category categoryEntity = this.categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);

        categoryEntity.setModifiedAt(LocalDateTime.now());
        categoryEntity.setName(updateCategoryRequest.name());

        Category updatedCategory = this.categoryRepository.save(categoryEntity);

        return updatedCategory;
    }

    public List<CategoryProductCountDTO> getCategoriesProductVarietyCount() {
        return this.categoryRepository.getCategoriesProductVarietyCount();
    }

    public Page<Category> getCategoriesPaginated(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return this.categoryRepository.findAll(pageRequest);
    }

    @Transactional
    public void changeCategoryActiveStatus(UUID id, boolean newActiveStatus){
        Integer modifiedRows = this.categoryRepository.changeCategoryActiveStatus(id, newActiveStatus);
    }
}
