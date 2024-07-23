package br.com.gabrielmarreiros.inventorymanagementangularjava.controllers;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.login.LoginRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.infra.JwtFilter;
import br.com.gabrielmarreiros.inventorymanagementangularjava.services.JwtService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthenticationController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class)})
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtService jwtService;

    /*login()*/

    @Test
    void givenACredentials_whenLogin_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        LoginRequestDTO loginRequestMock = mock(LoginRequestDTO.class);
        String loginRequestJson = objectMapper.writeValueAsString(loginRequestMock);

        Authentication authenticationMock = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authenticationMock);

        String testToken = "Test Token";
        when(jwtService.generateToken(any())).thenReturn(testToken);


        /*Action*/
        var response = mvc.perform(
            post("/auth/login")
                .content(loginRequestJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        /*Assert*/
        response
            .andExpect(status().isOk());
    }

    /*authenticate()*/

    @Test
    void givenAToken_whenAuthenticate_thenReturnStatus200Ok() throws Exception {
        /*Arrange*/
        String testToken = "Test Token";
        when(jwtService.validateToken(testToken)).thenThrow(JWTVerificationException.class);

        /*Action*/
        var response = mvc.perform(
            post("/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testToken)
        );

        /*Assert*/
        response
            .andExpect(status().isOk())
            .andExpect(content().string("false"));
    }
}