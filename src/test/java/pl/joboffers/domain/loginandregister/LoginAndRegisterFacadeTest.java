package pl.joboffers.domain.loginandregister;

import org.junit.Test;
import pl.joboffers.domain.loginandregister.loginandregisterdto.RegisterUserDto;
import pl.joboffers.domain.loginandregister.loginandregisterdto.RegistrationResultDto;
import pl.joboffers.domain.loginandregister.loginandregisterdto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class LoginAndRegisterFacadeTest {

    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(new LoginAndRegisterRepositoryTestImp());


    @Test
    public void should_find_user_by_username(){
        //given
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "pass");
        RegistrationResultDto register = loginAndRegisterFacade.register(registerUserDto);
        //when
        UserDto userByUsername = loginAndRegisterFacade.findByUsername(registerUserDto.username());
        //then
        assertThat(userByUsername).isEqualTo(new UserDto(register.id(), "username","pass"));
    }

    @Test
    public void should_register_user(){
        //given
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "pass");
        //when
        RegistrationResultDto register = loginAndRegisterFacade.register(registerUserDto);
        //then
        assertAll(
                ()-> assertThat(register.isCreated()).isTrue(),
                ()-> assertThat(register.username()).isEqualTo("username"));
    }
    @Test
    public void should_return_user_not_found_exception_when_user_is_not_found_by_username(){
        //given
        String username = "TestUserName";
        //when
        Throwable throwable = assertThrows(UsernameFoundException.class, () -> {
            loginAndRegisterFacade.findByUsername(username);
        });
        //then
        assertEquals("User not found", throwable.getMessage());

    }

}