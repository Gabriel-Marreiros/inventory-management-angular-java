package br.com.gabrielmarreiros.inventorymanagementangularjava.services;

import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Role;
import br.com.gabrielmarreiros.inventorymanagementangularjava.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles(){
        return this.roleRepository.findAll();
    }
}
