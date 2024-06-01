package br.com.gabrielmarreiros.inventorymanagementangularjava.controllers;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.user.SaveUserRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.user.UserResponseDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.user.UserUpdateRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.mappers.UserMapper;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.User;
import br.com.gabrielmarreiros.inventorymanagementangularjava.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        List<User> usersEntitiesList = this.userService.getAllUsers();

        List<UserResponseDTO> usersListResponse = this.userMapper.toResponseListDTO(usersEntitiesList);

        return ResponseEntity.ok(usersListResponse);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<UserResponseDTO>> getUsersPaginated(@RequestParam("page") int page, @RequestParam("size") int size){
        Page<User> usersEntityPage = this.userService.getUsersPaginated(page, size);

        Page<UserResponseDTO> usersResponsePage = this.userMapper.toResponsePageDTO(usersEntityPage);

        return ResponseEntity.ok(usersResponsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") UUID id){
        User userEntity = this.userService.getUserById(id);

        UserResponseDTO userResponse = this.userMapper.toResponseDTO(userEntity);

        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> saveUser(@RequestBody SaveUserRequestDTO saveUserRequest){
        User userEntity = this.userMapper.toEntity(saveUserRequest);

        User savedUser = this.userService.saveUser(userEntity);

        UserResponseDTO userResponse = this.userMapper.toResponseDTO(savedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateCategory(@PathVariable("id") UUID id, @RequestBody UserUpdateRequestDTO userUpdate){
        User updatedUserEntity = this.userService.updateUser(id, userUpdate);

        UserResponseDTO updatedUserResponse = this.userMapper.toResponseDTO(updatedUserEntity);

        return ResponseEntity.ok(updatedUserResponse);
    }

    @DeleteMapping(path = "{id}/change-active-status", params = {"active"})
    public ResponseEntity changeUserActiveStatus(@PathVariable("id") UUID id, @RequestParam("active") boolean newActiveStatus){
        this.userService.changeUserActiveStatus(id, newActiveStatus);
        return ResponseEntity.noContent().build();
    }
}
