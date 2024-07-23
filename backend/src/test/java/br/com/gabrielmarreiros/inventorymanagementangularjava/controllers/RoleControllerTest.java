package br.com.gabrielmarreiros.inventorymanagementangularjava.controllers;

import br.com.gabrielmarreiros.inventorymanagementangularjava.infra.JwtFilter;
import br.com.gabrielmarreiros.inventorymanagementangularjava.mappers.RoleMapper;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Product;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Role;
import br.com.gabrielmarreiros.inventorymanagementangularjava.services.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RoleController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class)})
class RoleControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RoleService roleService;
    @MockBean
    private RoleMapper roleMapper;

    /*getAllRoles()*/

    @Test
    void whenGetAllRoles_thenReturnStatus200Ok () throws Exception {
        /*Arrange*/
        List<Role> rolesList = new ArrayList<>();
        when(roleService.getAllRoles()).thenReturn(rolesList);

        /*Action*/
        var response = mvc.perform(
            get("/roles")
        );

        /*Assert*/
        response
            .andExpect(status().isOk());
    }
}