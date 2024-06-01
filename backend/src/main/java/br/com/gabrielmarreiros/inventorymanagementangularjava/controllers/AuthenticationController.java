package br.com.gabrielmarreiros.inventorymanagementangularjava.controllers;

import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.login.LoginRequestDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.dto.login.LoginResponseDTO;
import br.com.gabrielmarreiros.inventorymanagementangularjava.models.User;
import br.com.gabrielmarreiros.inventorymanagementangularjava.services.JwtService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO login){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.email(), login.password());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

        User user = (User) authentication.getPrincipal();

        String token = this.jwtService.generateToken(user);

        LoginResponseDTO loginResponse = new LoginResponseDTO(token);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Boolean> authenticate(@RequestBody String token){
        try{
            this.jwtService.validateToken(token);
        }
        catch (JWTVerificationException e){
            return ResponseEntity.ok(false);
        }

        return ResponseEntity.ok(true);
    }
}
