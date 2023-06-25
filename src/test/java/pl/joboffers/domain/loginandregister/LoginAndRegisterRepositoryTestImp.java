package pl.joboffers.domain.loginandregister;

import org.junit.Test;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class LoginAndRegisterRepositoryTestImp implements UserRepository{
    private final Map<String, User> inMemoryDatabase = new ConcurrentHashMap<>();
    @Override
    public Optional<User> findByUsername(String username) {
        return inMemoryDatabase.values()
                .stream()
                .filter(user -> user.username().equals(username))
                .findFirst();
    }
    @Override
    public User save(User user) {
        return inMemoryDatabase.put(user.id(), user);
    }

}
