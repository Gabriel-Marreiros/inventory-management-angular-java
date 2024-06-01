package br.com.gabrielmarreiros.inventorymanagementangularjava.repositories;

import br.com.gabrielmarreiros.inventorymanagementangularjava.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<UserDetails> findByEmail(String email);

    @Modifying
    @Query("""
        UPDATE
            User u
        SET
            u.active = :newActiveStatus
        WHERE
            u.id = :id
    """)
    Integer changeUserActiveStatus(@Param("id") UUID id, @Param("newActiveStatus") boolean newActiveStatus);
}
