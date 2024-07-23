package br.com.gabrielmarreiros.inventorymanagementangularjava.repositories;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.CategoryProductCountDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;

@DataJpaTest
@ActiveProfiles("test")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    /*getCategoriesProductVarietyCount()*/

    @Test
    void whenGetCategoriesProductVarietyCount_thenReturnAListOfCategoryProductCount() {
        /*Arrange*/

        /*Action*/
        List<CategoryProductCountDTO> repositoryResponse = categoryRepository.getCategoriesProductVarietyCount();

        /*Assert*/
        assertThatList(repositoryResponse)
                .isNotEmpty()
                .hasSize(3)
                .allMatch((c) -> c.productsVariety() == 4);

    }

    /*changeCategoryActiveStatus()*/

    @Test
    void givenAActiveCategory_whenChangeCategoryActiveStatus_thenDeactiveCategory() {
        /*Action*/
        UUID categoryId = UUID.fromString("e396a976-1a53-4a0a-871b-b1efde58c35a");
        Integer repositoryResponse = categoryRepository.changeCategoryActiveStatus(categoryId, false);

        Category modifiedCategory = categoryRepository.findById(categoryId).get();
        /*Assert*/
        assertThat(repositoryResponse)
                .isEqualTo(1);

        assertThat(modifiedCategory)
                .extracting("active")
                .isEqualTo(false);
    }

    @Test
    void givenAInactiveCategory_whenChangeCategoryActiveStatus_thenActiveCategory() {
        /*Action*/
        UUID categoryId = UUID.fromString("e626f851-25d3-40be-bb69-018dcbced34a");
        Integer repositoryResponse = categoryRepository.changeCategoryActiveStatus(categoryId, true);

        Category modifiedCategory = categoryRepository.findById(categoryId).get();
        /*Assert*/
        assertThat(repositoryResponse)
                .isEqualTo(1);

        assertThat(modifiedCategory)
                .extracting("active")
                .isEqualTo(true);
    }
}