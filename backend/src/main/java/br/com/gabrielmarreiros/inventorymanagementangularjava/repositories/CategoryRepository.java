package br.com.gabrielmarreiros.inventorymanagementangularjava.repositories;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.CategoryProductCountDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Query("""
        SELECT
            new br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.CategoryProductCountDTO(
                c.name,
                COUNT(p.id)
            )
        FROM
            Product p
        INNER JOIN
            Category c ON c.id = p.category.id
        GROUP BY
            c.id
        ORDER BY
            c.name ASC
    """)
    List<CategoryProductCountDTO> getCategoriesProductVarietyCount();

    @Modifying
    @Query("""
        UPDATE
            Category c
        SET
            c.active = :newActiveStatus
        WHERE
            c.id = :id
    """)
    Integer changeCategoryActiveStatus(@Param("id") UUID id, @Param("newActiveStatus") boolean newActiveStatus);
}
