package br.com.gabrielmarreiros.inventorymanagementangularjava.controllers;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.category.UpdateCategoryRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.CategoryProductCountDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.infra.JwtFilter;
import br.com.gabrielmarreiros.inventorymanagementangularjava.mappers.CategoryMapper;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Category;
import br.com.gabrielmarreiros.inventorymanagementangularjava.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class)})
class CategoryControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private CategoryMapper categoryMapper;

    /*getAllCategories()*/

    @Test
    void whenGetAllCategories_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        List<Category> categoryListMock = new ArrayList<>();
        when(categoryService.getAllCategories()).thenReturn(categoryListMock);

        /*Action*/
        var response = mvc.perform(
            get("/categories")
                .contentType(MediaType.APPLICATION_JSON)
        );

        /*Assert*/
        response
            .andExpect(status().isOk());
    }

    /*getCategoriesPaginated()*/

    @Test
    void givenAPageRequest_whenGetCategoriesPaginated_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        Page<Category> pageMock = mock(PageImpl.class);
        when(categoryService.getCategoriesPaginated(anyInt(), anyInt())).thenReturn(pageMock);

        /*Action*/
        var response = mvc.perform(
            get("/categories")
                .queryParam("page", "0")
                .queryParam("size", "1")
                .contentType(MediaType.APPLICATION_JSON)
        );

        /*Assert*/
        response
            .andExpect(status().isOk());
    }

    /*getCategoryById()*/

    @Test
    void whenGetCategoryById_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        Category categoryMock = mock(Category.class);
        UUID categoryId = UUID.randomUUID();
        when(categoryService.getCategoryById(categoryId)).thenReturn(categoryMock);

        /*Action*/
        var response = mvc.perform(
            get("/categories", categoryId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        /*Assert*/
        response
            .andExpect(status().isOk());
    }

    /*saveCategory()*/

    @Test
    void whenSaveCategory_thenReturnStatus201Created() throws Exception {
        /*Arrange*/
        Category categoryMock = mock(Category.class);
        when(categoryService.saveCategory(categoryMock)).thenReturn(categoryMock);

        /*Action*/
        var response = mvc.perform(
            post("/categories")
                .content(objectMapper.writeValueAsBytes(categoryMock))
                .contentType(MediaType.APPLICATION_JSON)
        );

        /*Assert*/
        response
            .andExpect(status().isCreated());
    }

    /*updateCategory()*/

    @Test
    void whenUpdateCategory_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        Category categoryMock = mock(Category.class);
        UpdateCategoryRequestDTO categoryUpdateMock = mock(UpdateCategoryRequestDTO.class);
        UUID categoryId = UUID.randomUUID();
        when(categoryService.updateCategory(categoryId, categoryUpdateMock)).thenReturn(categoryMock);

        /*Action*/
        var response = mvc.perform(
            put("/categories/" + categoryId)
                .content(objectMapper.writeValueAsString(categoryUpdateMock))
                .contentType(MediaType.APPLICATION_JSON)
        );

        /*Assert*/
        response
            .andExpect(status().isOk());
    }

    /*getCategoriesProductVarietyCount()*/

    @Test
    void whenGetCategoriesProductVarietyCount_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        List<CategoryProductCountDTO> categoryProductCountList = new ArrayList<>();
        when(categoryService.getCategoriesProductVarietyCount()).thenReturn(categoryProductCountList);

        /*Action*/
        var response = mvc.perform(
            get("/categories/products-variety-count")
                .accept(MediaType.APPLICATION_JSON)
        );

        /*Assert*/
        response
            .andExpect(status().isOk());
    }

    /*changeCategoryActiveStatus()*/

    @Test
    void whenChangeCategoryActiveStatus_thenReturnStatus204NoContent() throws Exception {
        /*Arrange*/
        UUID categoryId = UUID.randomUUID();
        doNothing().when(categoryService).changeCategoryActiveStatus(categoryId, true);

        /*Action*/
        var response = mvc.perform(
            delete("/categories/" + categoryId + "/change-active-status")
                .queryParam("active", "true")
                .accept(MediaType.APPLICATION_JSON)
        );

        /*Assert*/
        response
            .andExpect(status().isNoContent());
    }
}