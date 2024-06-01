package br.com.gabrielmarreiros.inventorymanagementangularjava.services;

import br.com.gabrielmarreiros.inventorymanagementangularjava.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private final Algorithm algorithm;
    private final String issuer = "Inventory Management Angular & Java";

    public JwtService(@Value("${app.token.secret}") String secretKey){
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    public String generateToken(User user){

        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", user.getId().toString());
        payload.put("name", user.getName());
        payload.put("roleId", user.getRole().getId().toString());
        payload.put("role", user.getRole().getName());

        try{
            String token = JWT.create()
            .withIssuer(this.issuer)
            .withSubject(user.getEmail())
            .withPayload(payload)
            .sign(this.algorithm);

            return token;
        }
        catch (JWTCreationException error){
            throw error;
        }
    }

    public String validateToken(String token){
        JWTVerifier jwtVerifier = JWT.require(this.algorithm)
        .withIssuer(this.issuer)
        .build();

        try {
            DecodedJWT decodedToken = jwtVerifier.verify(token);
            return decodedToken.getSubject();
        }
        catch (JWTVerificationException error){
            throw error;
        }
    }
}
