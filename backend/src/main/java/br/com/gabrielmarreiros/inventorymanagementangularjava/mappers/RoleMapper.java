package br.com.gabrielmarreiros.inventorymanagementangularjava.mappers;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.role.RoleResponseDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Role;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleMapper {

    public RoleResponseDTO toResponseDTO(Role roleEntity) {
        return new RoleResponseDTO(
                roleEntity.getId(),
                roleEntity.getName()
        );
    }

    public List<RoleResponseDTO> toResponseListDTO(List<Role> rolesEntityList) {
        return rolesEntityList.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}
