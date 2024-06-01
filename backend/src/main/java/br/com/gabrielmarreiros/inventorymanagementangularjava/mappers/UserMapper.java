package br.com.gabrielmarreiros.inventorymanagementangularjava.mappers;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.user.SaveUserRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.user.UserResponseDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Role;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public User toEntity(SaveUserRequestDTO saveUserRequest){
        Role role = new Role(saveUserRequest.role());

        User userEntity = new User();
        userEntity.setName(saveUserRequest.name());
        userEntity.setEmail(saveUserRequest.email());
        userEntity.setPassword(saveUserRequest.password());
        userEntity.setPhoneNumber(saveUserRequest.phoneNumber());
        userEntity.setDateBirth(saveUserRequest.dateBirth());
        userEntity.setRole(role);

        return userEntity;
    }

    public UserResponseDTO toResponseDTO(User userEntity){
        return new UserResponseDTO(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPhoneNumber(),
                userEntity.getDateBirth(),
                userEntity.getActive(),
                userEntity.getRole()
        );
    }

    public List<UserResponseDTO> toResponseListDTO(List<User> usersEntitiesList){
        return usersEntitiesList.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public Page<UserResponseDTO> toResponsePageDTO(Page<User> usersEntityPage) {
        return usersEntityPage.map(this::toResponseDTO);
    }
}
