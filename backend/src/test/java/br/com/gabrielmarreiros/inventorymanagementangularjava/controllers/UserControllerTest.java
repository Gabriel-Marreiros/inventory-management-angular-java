package br.com.gabrielmarreiros.inventorymanagementangularjava.controllers;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.ProductUpdateRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.product.SaveProductRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.user.SaveUserRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.user.UserUpdateRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.infra.JwtFilter;
import br.com.gabrielmarreiros.inventorymanagementangularjava.mappers.UserMapper;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Product;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Role;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.User;
import br.com.gabrielmarreiros.inventorymanagementangularjava.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class)})
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;

    /*getAllUsers()*/

    @Test
    void whenGetAllUsers_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        List<User> userList = new ArrayList<>();
        when(userService.getAllUsers()).thenReturn(userList);

        /*Action*/
        var response = mvc.perform(
                get("/users")
        );

        /*Assert*/
        response
            .andExpect(status().isOk());
    }

    /*getUsersPaginated()*/

    @Test
    void whenGetUsersPaginated_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        Page<User> usersPage = mock(PageImpl.class);
        when(userService.getUsersPaginated(anyInt(), anyInt())).thenReturn(usersPage);

        /*Action*/
        var response = mvc.perform(
                get("/users/paginated")
                        .queryParam("page", "0")
                        .queryParam("size", "1")
        );

        /*Assert*/
        response
                .andExpect(status().isOk());
    }

    /*getUserById()*/

    @Test
    void whenGetUserById_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        User userMock = mock(User.class);
        UUID userId = UUID.randomUUID();
        when(userService.getUserById(userId)).thenReturn(userMock);

        /*Action*/
        var response = mvc.perform(
            get("/users", userId)
        );

        /*Assert*/
        response
            .andExpect(status().isOk());
    }

    /*saveUser()*/

    @Test
    void whenSaveUser_thenReturnStatus201Created() throws Exception {
        /*Arrange*/
        SaveUserRequestDTO saveUserRequestMock = mock(SaveUserRequestDTO.class);
        User userMock = mock(User.class);
        when(userService.saveUser(any(User.class))).thenReturn(userMock);

        /*Action*/
        var response = mvc.perform(
                post("/users")
                        .content(objectMapper.writeValueAsString(saveUserRequestMock))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        /*Assert*/
        response
            .andExpect(status().isCreated());
    }

    /*updateCategory()*/

    @Test
    void whenUpdateCategory_thenReturnStatus201Created() throws Exception {
        /*Arrange*/
        UserUpdateRequestDTO userUpdateRequestMock = mock(UserUpdateRequestDTO.class);
        User userMock = mock(User.class);
        UUID userId = UUID.randomUUID();
        when(userService.updateUser(userId, userUpdateRequestMock)).thenReturn(userMock);

        /*Action*/
        var response = mvc.perform(
            post("/users", userId)
                .content(objectMapper.writeValueAsString(userUpdateRequestMock))
                .contentType(MediaType.APPLICATION_JSON)
        );

        /*Assert*/
        response
            .andExpect(status().isCreated());
    }

    /*changeUserActiveStatus()*/

    @Test
    void whenChangeUserActiveStatus_thenReturnStatus204NoContent() throws Exception {
        /*Arrange*/
        UUID userId = UUID.randomUUID();
        doNothing().when(userService).changeUserActiveStatus(userId, false);

        /*Action*/
        var response = mvc.perform(
            delete("/users/" + userId +"/change-active-status")
                .queryParam("active", "false")
        );

        /*Assert*/
        response
            .andExpect(status().isNoContent());
    }
}