package br.com.gabrielmarreiros.inventorymanagementangularjava.services;

import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Role;
import br.com.gabrielmarreiros.inventorymanagementangularjava.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleService roleService;

    @BeforeEach
    void initMocks(){
        openMocks(this);
    }

//  getAllRoles()
    @Test
    void whenRequesting_thenReturnAListOfAllRoles() {
//        Arrange
        Role roleMock = mock(Role.class);
        when(roleRepository.findAll()).thenReturn(List.of(roleMock));

//        Action
        List<Role> response = roleService.getAllRoles();

//        Assert
        assertThat(response)
            .isEqualTo(List.of(roleMock));
    }
}