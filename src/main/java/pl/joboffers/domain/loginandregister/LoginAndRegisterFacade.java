package pl.joboffers.domain.loginandregister;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import pl.joboffers.domain.loginandregister.loginandregisterdto.RegisterUserDto;
import pl.joboffers.domain.loginandregister.loginandregisterdto.RegistrationResultDto;
import pl.joboffers.domain.loginandregister.loginandregisterdto.UserDto;



@AllArgsConstructor
@Component
public class LoginAndRegisterFacade {
    private static final String USER_NOT_FOUND = "User not found";
    private final UserRepository userRepository;

    public UserDto findByUsername(String username){
        return userRepository.findByUsername(username)
                .map(user -> new UserDto(user.id(), user.username(), user.password()))
                .orElseThrow(() -> new BadCredentialsException(USER_NOT_FOUND));
    }
    public RegistrationResultDto register(RegisterUserDto registerUserDto){
        final User user = User
                .builder()
                .username(registerUserDto.username())
                .password(registerUserDto.password())
                .build();
        User savedUser = userRepository.save(user);
        return new RegistrationResultDto(savedUser.id(), true, savedUser.username());
    }
}
