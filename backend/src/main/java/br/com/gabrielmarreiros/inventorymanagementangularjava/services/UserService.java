package br.com.gabrielmarreiros.inventorymanagementangularjava.services;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.user.UserUpdateRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions.UserAlreadyExistsException;
import br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions.UserNotFoundException;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Role;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.User;
import br.com.gabrielmarreiros.inventorymanagementangularjava.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public User saveUser(User user) {

        if (this.userAlreadyExists(user)) {
            throw new UserAlreadyExistsException();
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = this.userRepository.save(user);

        return savedUser;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public boolean userAlreadyExists(User user) {
        return this.userRepository.findByEmail(user.getEmail()).isPresent();
    }

    public Page<User> getUsersPaginated(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return this.userRepository.findAll(pageRequest);
    }

    @Transactional
    public User updateUser(UUID id, UserUpdateRequestDTO userUpdate) {
        User userEntity = this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        userEntity.setName(userUpdate.name());
        userEntity.setEmail(userUpdate.email());
        userEntity.setPhoneNumber(userUpdate.phoneNumber());
        userEntity.setDateBirth(userUpdate.dateBirth());
        userEntity.setRole(new Role(userUpdate.roleId()));

        User updatedUser = this.userRepository.save(userEntity);

        return updatedUser;
    }

    @Transactional
    public void changeUserActiveStatus(UUID id, boolean newActiveStatus) {
        Integer modifiedRows = this.userRepository.changeUserActiveStatus(id, newActiveStatus);
    }
}
