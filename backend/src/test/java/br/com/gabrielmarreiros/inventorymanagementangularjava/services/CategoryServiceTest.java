package br.com.gabrielmarreiros.inventorymanagementangularjava.services;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.category.UpdateCategoryRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.CategoryProductCountDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions.CategoryNotFoundException;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Category;
import br.com.gabrielmarreiros.inventorymanagementangularjava.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void initMocks(){
        openMocks(this);
    }

    /*getAllCategories()*/

    @Test
    void whenGetAllCategories_thenReturnAListOfAllCategories() {
        /*Arrange*/
        Category categoryMock = mock(Category.class);
        List<Category> categoriesList = new ArrayList<>();
        categoriesList.add(categoryMock);

        when(categoryRepository.findAll()).thenReturn(categoriesList);

        /*Action*/
        List<Category> serviceResponse = categoryService.getAllCategories();

        /*Assert*/
        assertThat(serviceResponse)
                .isNotEmpty()
                .isEqualTo(categoriesList);

    }

    /*getCategoryById()*/

    @Test
    void givenAValidId_whenGetCategoryById_thenReturnCategory() {
        /*Arrange*/
        UUID categoryId = UUID.randomUUID();
        Category categoryMock = mock(Category.class);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryMock));

        /*Action*/
        Category serviceResponse = categoryService.getCategoryById(categoryId);

        /*Assert*/
        assertThat(serviceResponse)
                .isInstanceOf(Category.class)
                .isEqualTo(categoryMock);

    }

    @Test
    void givenAUnknownId_whenGetCategoryById_thenThrowsCategoryNotFoundException() {
        /*Arrange*/
        UUID categoryId = UUID.randomUUID();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        /*Action*/
        Exception serviceResponse = catchException(() -> categoryService.getCategoryById(categoryId));

        /*Assert*/
        assertThat(serviceResponse)
                .isInstanceOf(CategoryNotFoundException.class);

    }

    /*saveCategory()*/

    @Test
    void whenSaveCategory_thenSaveAndReturnCategory() {
        /*Arrange*/
        Category categoryMock = mock(Category.class);
        when(categoryRepository.save(categoryMock)).thenReturn(categoryMock);

        /*Action*/
        Category serviceResponse = categoryService.saveCategory(categoryMock);

        /*Assert*/
        assertThat(serviceResponse)
                .isEqualTo(categoryMock);

    }

    /*updateCategory()*/

    @Test
    void givenAValidId_whenUpdateCategory_thenReturnCategory() {
        /*Arrange*/
        UUID categoryId = UUID.randomUUID();
        UpdateCategoryRequestDTO categoryUpdateMock = mock(UpdateCategoryRequestDTO.class);
        Category categoryMock = mock(Category.class);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryMock));
        when(categoryRepository.save(categoryMock)).thenReturn(categoryMock);

        /*Action*/
        Category serviceResponse = categoryService.updateCategory(categoryId, categoryUpdateMock);

        /*Assert*/
        assertThat(serviceResponse)
                .isInstanceOf(Category.class)
                .isEqualTo(categoryMock);
    }

    @Test
    void givenAUnknownId_whenUpdateCategory_thenThrowsCategoryNotFoundException() {
        /*Arrange*/
        UUID categoryId = UUID.randomUUID();
        UpdateCategoryRequestDTO categoryUpdateMock = mock(UpdateCategoryRequestDTO.class);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        /*Action*/
        Exception serviceResponse = catchException(() -> categoryService.updateCategory(categoryId, categoryUpdateMock));

        /*Assert*/
        assertThat(serviceResponse)
                .isInstanceOf(CategoryNotFoundException.class);

    }

    /*getCategoriesProductVarietyCount()*/

    @Test
    void whenGetCategoriesProductVarietyCount_thenReturnAListOfCategoriesProductVarietyCount() {
        /*Arrange*/
        CategoryProductCountDTO categoryProductCountMock = mock(CategoryProductCountDTO.class);
        List<CategoryProductCountDTO> categoryProductCountList = new ArrayList<>();
        categoryProductCountList.add(categoryProductCountMock);

        when(categoryRepository.getCategoriesProductVarietyCount()).thenReturn(categoryProductCountList);
        /*Action*/
        List<CategoryProductCountDTO> serviceResponse = categoryService.getCategoriesProductVarietyCount();

        /*Assert*/
        assertThat(serviceResponse)
                .isEqualTo(categoryProductCountList);

    }

    /*getCategoriesPaginated()*/

    @Test
    void whenGetCategoriesPaginated_thenReturnAPageOfCategories() {
        /*Arrange*/
        Page<Category> categoriesPage = mock(PageImpl.class);
        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(categoriesPage);

        /*Action*/
        Page<Category> serviceResponse = categoryService.getCategoriesPaginated(0, 1);

        /*Assert*/
        assertThat(serviceResponse)
                .isEqualTo(categoriesPage);

    }
}