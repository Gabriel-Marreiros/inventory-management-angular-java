package br.com.gabrielmarreiros.inventorymanagementangularjava.infra;

import br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions.UserNotFoundException;
import br.com.gabrielmarreiros.inventorymanagementangularjava.services.JwtService;
import br.com.gabrielmarreiros.inventorymanagementangularjava.services.UserService;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtService jwtService;
    public JwtFilter(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJwt = this.getTokenFromRequest(request);

        if(!tokenJwt.isEmpty()){
            try{
                String subject = this.jwtService.validateToken(tokenJwt);
                UserDetails userDetails = userService.loadUserByUsername(subject);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            catch (UserNotFoundException | SignatureVerificationException e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request){
        Optional<String> tokenJwt = Optional.ofNullable(request.getHeader("Authorization"));

        if(tokenJwt.isEmpty()){
            return "";
        }

        return tokenJwt.get().replace("Bearer ", "");
    }
}
