package br.com.gabrielmarreiros.inventorymanagementangularjava.repositories;

import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
}
