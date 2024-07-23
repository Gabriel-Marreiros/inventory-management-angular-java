package br.com.gabrielmarreiros.inventorymanagementangularjava.services;

import br.com.gabrielmarreiros.inventorymanagementangularjava.models.Role;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.User;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
@ActiveProfiles("test")
class JwtServiceTest {
    @Autowired
    private JwtService jwtService;
    private User testUser;
    private String testToken;

    @BeforeEach
    void initMocks(){
        openMocks(this);
    }

    @BeforeEach
    void setUpTestProperties(){
//        Test Token
        this.testToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJJbnZlbnRvcnkgTWFuYWdlbWVudCBBbmd1bGFyICYgSmF2YSIsInN1YiI6bnVsbCwicm9sZSI6IlRlc3QgUm9sZSIsInJvbGVJZCI6ImE2NWUwNGU1LTY1ZDktNGZhZS1iNTFkLTQ1MjMwY2I0OGY5YyIsIm5hbWUiOiJUZXN0IE5hbWUiLCJ1c2VySWQiOiI4MDliODJhZi1lZmJlLTRkYTMtOTZhYi1lMTE3ZmZjZWU0ZDYifQ.9aDiEcw38PZ_vMEmj3_qqQQo8LrBwNVbXkos_jwVECA";

//        Test User
        Role testRole = new Role();
        testRole.setId(UUID.fromString("a65e04e5-65d9-4fae-b51d-45230cb48f9c"));
        testRole.setName("Test Role");

        User testUser = new User();
        testUser.setId(UUID.fromString("809b82af-efbe-4da3-96ab-e117ffcee4d6"));
        testUser.setName("Test Name");
        testUser.setRole(testRole);

        this.testUser = testUser;
    }

//  generateToken()

    @Test
    void givenAUser_whenGenerateToken_thenGenerateToken() {
//            Action
        String serviceResponse = jwtService.generateToken(testUser);

//            Assert
        assertThat(serviceResponse)
                .isEqualTo(this.testToken);
    }

//  validateToken()

    @Test
    void givenAValidToken_whenValidateToken_thenReturnTokenSubject() {
//        Action
        String serviceResponse = jwtService.validateToken(this.testToken);

//        Assert
        assertThat(serviceResponse)
                .isEqualTo(this.testUser.getEmail());

    }

    @Test
    void givenAInvalidToken_whenValidateToken_thenThrowsJWTVerificationException() {
//        Arrange
        String invalidToken = "Invalid Token";

//        Assert
        assertThatException()
                .isThrownBy(() -> jwtService.validateToken(invalidToken))
                .isInstanceOf(JWTVerificationException.class);
    }

}