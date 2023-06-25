package pl.joboffers.domain.loginandregister;

import pl.joboffers.domain.loginandregister.loginandregisterdto.UserDto;

import java.util.Optional;

public interface UserRepository {


    Optional<User> findByUsername(String username);

    User save(User user);
}
