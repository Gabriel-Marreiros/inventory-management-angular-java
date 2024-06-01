package br.com.gabrielmarreiros.inventorymanagementangularjava.repositories;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.BrandsProductsStatusReportDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.ProductsNumberRegisteredMonthReportDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.SummaryProductsCategoriesUsersActiveDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

//    findBySearchTermIgnoreCaseContaining
    Page<Product> findBySearchTermIgnoreCaseContaining(String searchFilter, PageRequest pageRequest);

    @Query("""
        SELECT
            p
        FROM
            Product p
        WHERE
            p.createdAt IS NOT NULL
        ORDER BY
            p.createdAt DESC
        LIMIT
            10
    """)
    List<Product> findLastTenRegistered();

    @Query("""
        SELECT new br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.BrandsProductsStatusReportDTO(
            p.brand,
            SUM(CASE WHEN p.active = TRUE THEN 1 ELSE 0 END),
            SUM(CASE WHEN p.active = FALSE THEN 1 ELSE 0 END)
        )
        FROM
            Product p
        GROUP BY
            p.brand
        ORDER BY
            p.brand ASC
    """)
    List<BrandsProductsStatusReportDTO> getBrandsProductsStatusReport();


    @Query("""
        SELECT
            new br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.ProductsNumberRegisteredMonthReportDTO(
                months.name,
                SUM(CASE WHEN MONTH(p.createdAt) = months.num THEN 1 ELSE 0 END)
            )
        FROM
            Product p
        RIGHT JOIN
            (
                SELECT 'Dezembro' AS name, 12 AS num
                UNION SELECT 'Novembro', 11
                UNION SELECT 'Outubro', 10
                UNION SELECT 'Setembro', 9
                UNION SELECT 'Agosto', 8
                UNION SELECT 'Julho', 7
                UNION SELECT 'Junho', 6
                UNION SELECT 'Maio', 5
                UNION SELECT 'Abril', 4
                UNION SELECT 'Março', 3
                UNION SELECT 'Fevereiro', 2
                UNION SELECT 'Janeiro', 1
            ) AS months ON TRUE
        WHERE
            YEAR(p.createdAt) = :year
        GROUP BY
            months.name
    """)
    List<ProductsNumberRegisteredMonthReportDTO> getProductsNumberRegisteredMonthReport(@Param("year") String year);

    @Modifying
    @Query("""
        UPDATE
            Product p
        SET
            p.active = :newActiveStatus
        WHERE
            p.id = :id
    """)
    Integer changeProductActiveStatus(@Param("id") UUID id, @Param("newActiveStatus") boolean newActiveStatus);

    @Query("""
        SELECT
            new br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.SummaryProductsCategoriesUsersActiveDTO(
                COUNT(DISTINCT p.id),
                COUNT(DISTINCT c.id),
                COUNT(DISTINCT u.id)
            )
        FROM
            Product p,
            Category c,
            User u
        WHERE
            p.active = TRUE AND c.active = TRUE AND u.active = TRUE
    """)
    SummaryProductsCategoriesUsersActiveDTO getSummaryProductsCategoriesUsersActive();
}
