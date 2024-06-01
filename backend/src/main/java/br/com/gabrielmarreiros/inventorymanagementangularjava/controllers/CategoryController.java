package br.com.gabrielmarreiros.inventorymanagementangularjava.controllers;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.category.CategoryResponseDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.category.SaveCategoryRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.category.UpdateCategoryRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.CategoryProductCountDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.mappers.CategoryMapper;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Category;
import br.com.gabrielmarreiros.inventorymanagementangularjava.services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        List<Category> categoriesList = this.categoryService.getAllCategories();

        List<CategoryResponseDTO> categoriesListResponse = this.categoryMapper.toResponseListDTO(categoriesList);

        return ResponseEntity.ok(categoriesListResponse);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<CategoryResponseDTO>> getCategoriesPaginated(@RequestParam("page") int page, @RequestParam("size") int size){
        Page<Category> categoriesEntityPage = this.categoryService.getCategoriesPaginated(page, size);

        Page<CategoryResponseDTO> categoriesResponsePage = this.categoryMapper.toResponsePageDTO(categoriesEntityPage);

        return ResponseEntity.ok(categoriesResponsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable("id") UUID id){
        Category categoryEntity = this.categoryService.getCategoryById(id);

        CategoryResponseDTO categoryResponse = this.categoryMapper.toResponseDTO(categoryEntity);

        return ResponseEntity.ok(categoryResponse);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> saveCategory(@RequestBody SaveCategoryRequestDTO categorySaveRequest){
        Category categoryEntity = this.categoryMapper.toEntity(categorySaveRequest);

        Category savedCategory = this.categoryService.saveCategory(categoryEntity);

        CategoryResponseDTO categoryResponse = this.categoryMapper.toResponseDTO(savedCategory);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable("id") UUID id, @RequestBody UpdateCategoryRequestDTO updateCategoryRequest){
        Category updatedCategory = this.categoryService.updateCategory(id, updateCategoryRequest);

        CategoryResponseDTO categoryResponse = this.categoryMapper.toResponseDTO(updatedCategory);

        return ResponseEntity.ok(categoryResponse);
    }

    @GetMapping("/products-variety-count")
    public ResponseEntity<List<CategoryProductCountDTO>> getCategoriesProductVarietyCount(){
        List<CategoryProductCountDTO> categoriesProductVarietyCount = this.categoryService.getCategoriesProductVarietyCount();

        return ResponseEntity.ok(categoriesProductVarietyCount);
    }

    @DeleteMapping(path = "{id}/change-active-status", params = {"active"})
    public ResponseEntity changeCategoryActiveStatus(@PathVariable("id") UUID id, @RequestParam("active") boolean newActiveStatus){
        this.categoryService.changeCategoryActiveStatus(id, newActiveStatus);
        return ResponseEntity.noContent().build();
    }

}
