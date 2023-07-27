package pl.joboffers.infrastructure.security.jwt;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.joboffers.domain.loginandregister.loginandregisterdto.RegisterUserDto;
import pl.joboffers.infrastructure.loginandregister.controller.JwtResultDto;

@AllArgsConstructor
@Component
public class JWTAuthenticatorFacade {

    private final AuthenticationManager authenticationManager;
    public JwtResultDto authenticateAndGenerateToken(RegisterUserDto registerUserDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerUserDto.username(),registerUserDto.password())
        );
        return JwtResultDto.builder().build();
    }
}
