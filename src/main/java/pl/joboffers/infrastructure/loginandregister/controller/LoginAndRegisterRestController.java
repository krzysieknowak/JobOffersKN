package pl.joboffers.infrastructure.loginandregister.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.joboffers.domain.loginandregister.LoginAndRegisterFacade;
import pl.joboffers.domain.loginandregister.loginandregisterdto.RegisterUserDto;
import pl.joboffers.domain.loginandregister.loginandregisterdto.RegistrationResultDto;
import pl.joboffers.infrastructure.security.jwt.JWTAuthenticatorFacade;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class LoginAndRegisterRestController {

    private final JWTAuthenticatorFacade jwtAuthenticatorFacade;
    private final LoginAndRegisterFacade loginAndRegisterFacade;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/token")
    public ResponseEntity<JwtResultDto> authenticateAndGenerateToken(@Valid @RequestBody RegisterUserDto registerUserDto){
        final JwtResultDto jwtResponse = jwtAuthenticatorFacade.authenticateAndGenerateToken(registerUserDto);
        return ResponseEntity.ok(jwtResponse);
    }
    @PostMapping("/register")
    public ResponseEntity<RegistrationResultDto> registerUser(@RequestBody RegisterUserDto registerUserDto){
        String encodedPass = passwordEncoder.encode(registerUserDto.password());
        RegistrationResultDto registeredUser = loginAndRegisterFacade.register(new RegisterUserDto(registerUserDto.username(), encodedPass));
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
}
