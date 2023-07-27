package pl.joboffers.infrastructure.loginandregister.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.joboffers.domain.loginandregister.loginandregisterdto.RegisterUserDto;
import pl.joboffers.infrastructure.security.jwt.JWTAuthenticatorFacade;

import javax.validation.Valid;

@RestController
@AllArgsConstructor

public class LoginAndRegisterRestController {
    private final JWTAuthenticatorFacade jwtAuthenticatorFacade;
    @PostMapping("/token")
    public ResponseEntity<JwtResultDto> authenticateAndGenerateToken(@Valid @RequestBody RegisterUserDto registerUserDto){
        final JwtResultDto jwtResponse = jwtAuthenticatorFacade.authenticateAndGenerateToken(registerUserDto);
        return ResponseEntity.ok(jwtResponse);
    }
}
