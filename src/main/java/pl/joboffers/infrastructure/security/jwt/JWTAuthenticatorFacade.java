package pl.joboffers.infrastructure.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import pl.joboffers.domain.loginandregister.loginandregisterdto.RegisterUserDto;
import pl.joboffers.infrastructure.loginandregister.controller.JwtResultDto;
import pl.joboffers.infrastructure.security.jwt.error.JwtAuthConfigurationProperties;

import java.time.*;

@AllArgsConstructor
@Component
public class JWTAuthenticatorFacade {

    private final AuthenticationManager authenticationManager;
    private final JwtAuthConfigurationProperties properties;
    private final Clock clock;

    public JwtResultDto authenticateAndGenerateToken(RegisterUserDto registerUserDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerUserDto.username(), registerUserDto.password()));
        User user = (User) authentication.getPrincipal();
        String token = createToken(user);
        String username = user.getUsername();
        return JwtResultDto.builder()
                .username(username)
                .token(token)
                .build();
    }

    private String createToken(User user) {

        String secretKey = properties.secret();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Instant now = LocalDateTime.now(clock).toInstant(ZoneOffset.UTC);
        Instant expiredAt = now.plus(Duration.ofDays(properties.expirationInDays()));
        return JWT.create()
                .withIssuer(properties.issuer())
                .withIssuedAt(now)
                .withExpiresAt(expiredAt)
                .withSubject(user.getUsername())
                .sign(algorithm);
    }
}
