package pl.joboffers.domain.loginandregister;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LoginAndRegisterRepositoryTestImp implements UserRepository {
    Map<String, User> inMemoryDatabase = new ConcurrentHashMap<>();

    @Override
    public Optional<User> findByUsername(String username) {
        return inMemoryDatabase.values()
                .stream()
                .filter(user -> user.username().equals(username))
                .findFirst();
    }

    @Override
    public User save(User entity) {
        UUID generatedId = UUID.randomUUID();
        User user = User
                .builder()
                .id(generatedId.toString())
                .username(entity.username())
                .password(entity.password())
                .build();
        inMemoryDatabase.put(user.username(), user);
        return user;
    }
}
