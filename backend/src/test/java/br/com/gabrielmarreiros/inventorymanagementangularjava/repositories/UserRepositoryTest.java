package br.com.gabrielmarreiros.inventorymanagementangularjava.repositories;

import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Category;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /*findByEmail()*/

    @Test
    void givenAEmail_whenFindByEmail_thenReturnTheUserWithSearchEmail() {
        /*Arrange*/
        String userEmail = "usuario-1@email.com";

        /*Action*/
        var repositoryResponse = userRepository.findByEmail(userEmail);

        /*Assert*/
        assertThat(repositoryResponse)
                .hasValueSatisfying((userDetails) -> userDetails.getUsername().equals(userEmail));
    }

    /*changeUserActiveStatus()*/

    @Test
    void givenAActiveCategory_whenChangeUserActiveStatus_thenDeactiveCategory() {
        /*Action*/
        UUID userId = UUID.fromString("718c5016-b159-410f-9e4b-babeaa10bf6a");
        Integer repositoryResponse = userRepository.changeUserActiveStatus(userId, false);

        User modifiedCategory = userRepository.findById(userId).get();
        /*Assert*/
        assertThat(repositoryResponse)
                .isEqualTo(1);

        assertThat(modifiedCategory)
                .extracting("active")
                .isEqualTo(false);
    }

    @Test
    void givenAInactiveCategory_whenChangeUserActiveStatus_thenActiveCategory() {
        /*Action*/
        UUID userId = UUID.fromString("02c72eea-daa5-4ddc-8929-90398761bc4a");
        Integer repositoryResponse = userRepository.changeUserActiveStatus(userId, true);

        User modifiedCategory = userRepository.findById(userId).get();
        /*Assert*/
        assertThat(repositoryResponse)
                .isEqualTo(1);

        assertThat(modifiedCategory)
                .extracting("active")
                .isEqualTo(true);
    }
}