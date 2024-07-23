package br.com.gabrielmarreiros.inventorymanagementangularjava.services;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.user.UserUpdateRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions.UserAlreadyExistsException;
import br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions.UserNotFoundException;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.User;
import br.com.gabrielmarreiros.inventorymanagementangularjava.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    void initMocks(){
        openMocks(this);
    }

    /*saveUser()*/

    @Test
    void whenSaveUser_thenSaveAndReturnUser() {
        /*Arrange*/
        User userMock = mock(User.class);
        when(userRepository.save(userMock)).thenReturn(userMock);

        /*Action*/
        User serviceResponse = userService.saveUser(userMock);

        /*Assert*/
        assertThat(serviceResponse)
                .isInstanceOf(User.class)
                .isEqualTo(userMock);

    }

    @Test
    void givenAUserThatAlreadyExists_whenSaveUser_thenThrowsUserAlreadyExistsException() {
        /*Arrange*/
        User userMock = mock(User.class);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(userMock));

        /*Action*/
        Exception serviceResponse = catchException(() -> userService.saveUser(userMock));

        /*Assert*/
        assertThat(serviceResponse)
                .isInstanceOf(UserAlreadyExistsException.class);
    }

    /*getAllUsers()*/

    @Test
    void whenGetAllUsers_thenReturnAListOfAllUsers() {
        /*Arrange*/
        User userMock = mock(User.class);
        List<User> usersList = new ArrayList<>();
        usersList.add(userMock);

        when(userRepository.findAll()).thenReturn(usersList);

        /*Action*/
        List<User> serviceResponse = userService.getAllUsers();

        /*Assert*/
        assertThat(serviceResponse)
                .isNotEmpty()
                .isEqualTo(usersList);

    }

    /*getUserById()*/

    @Test
    void givenAValidId_whenGetUserById_thenReturnCategory() {
        /*Arrange*/
        UUID userId = UUID.randomUUID();
        User userMock = mock(User.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userMock));

        /*Action*/
        User serviceResponse = userService.getUserById(userId);

        /*Assert*/
        assertThat(serviceResponse)
                .isInstanceOf(User.class)
                .isEqualTo(userMock);

    }

    @Test
    void givenAUnknownId_whenGetUserById_thenThrowsUserNotFoundException() {
        /*Arrange*/
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        /*Action*/
        Exception serviceResponse = catchException(() -> userService.getUserById(userId));

        /*Assert*/
        assertThat(serviceResponse)
                .isInstanceOf(UserNotFoundException.class);

    }

    /*userAlreadyExists()*/

    @Test
    void givenAUserThatAlreadyExists_whenExecuteUserAlreadyExistsMethod_thenReturnTrue() {
        /*Arrange*/
        User userMock = mock(User.class);
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(userMock));

        /*Action*/
        boolean serviceResponse = userService.userAlreadyExists(userMock);

        /*Assert*/
        assertThat(serviceResponse)
                .isTrue();

    }

    @Test
    void givenAUserThatDoesNotExist_whenExecuteUserAlreadyExistsMethod_thenReturnFalse() {
        /*Arrange*/
        User userMock = mock(User.class);
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        /*Action*/
        boolean serviceResponse = userService.userAlreadyExists(userMock);

        /*Assert*/
        assertThat(serviceResponse)
                .isFalse();

    }

    /*getUsersPaginated()*/

    @Test
    void whenGetUsersPaginated_thenReturnAPageOfUsers() {
        /*Arrange*/
        Page<User> usersPage = mock(PageImpl.class);
        when(userRepository.findAll(any(PageRequest.class))).thenReturn(usersPage);

        /*Action*/
        Page<User> serviceResponse = userService.getUsersPaginated(0, 1);

        /*Assert*/
        assertThat(serviceResponse)
                .isEqualTo(usersPage);

    }

    /*updateUser()*/

    @Test
    void givenAValidId_whenUpdateUser_thenReturnCategory() {
        /*Arrange*/
        UUID userId = UUID.randomUUID();
        UserUpdateRequestDTO userUpdateMock = mock(UserUpdateRequestDTO.class);
        User userMock = mock(User.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userMock));
        when(userRepository.save(any(User.class))).thenReturn(userMock);

        /*Action*/
        User serviceResponse = userService.updateUser(userId, userUpdateMock);

        /*Assert*/
        assertThat(serviceResponse)
                .isInstanceOf(User.class)
                .isEqualTo(userMock);

    }

    @Test
    void givenAUnknownId_whenUpdateUser_thenThrowsUserNotFoundException() {
        /*Arrange*/
        UUID userId = UUID.randomUUID();
        UserUpdateRequestDTO userUpdateMock = mock(UserUpdateRequestDTO.class);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        /*Action*/
        Exception serviceResponse = catchException(() -> userService.updateUser(userId, userUpdateMock));

        /*Assert*/
        assertThat(serviceResponse)
                .isInstanceOf(UserNotFoundException.class);

    }
}