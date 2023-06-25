package pl.joboffers.domain.loginandregister;

import lombok.AllArgsConstructor;
import pl.joboffers.domain.loginandregister.loginandregisterdto.RegisterUserDto;
import pl.joboffers.domain.loginandregister.loginandregisterdto.RegistrationResultDto;
import pl.joboffers.domain.loginandregister.loginandregisterdto.UserDto;

@AllArgsConstructor
public class LoginAndRegisterFacade {
    private static final String USER_NOT_FOUND = "User not found";
    private final UserRepository userRepository;

    public UserDto findByUsername(String username){
        return userRepository.findByUsername(username)
                .map(user -> new UserDto(user.id(), user.username(), user.password()))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
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
