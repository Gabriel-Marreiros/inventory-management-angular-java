package br.com.gabrielmarreiros.inventorymanagementangularjava.controllers;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.role.RoleResponseDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.mappers.RoleMapper;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Role;
import br.com.gabrielmarreiros.inventorymanagementangularjava.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public RoleController(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles(){
        List<Role> rolesEntityList = this.roleService.getAllRoles();

        List<RoleResponseDTO> rolesResponseList = this.roleMapper.toResponseListDTO(rolesEntityList);

        return ResponseEntity.ok(rolesResponseList);
    }
}
