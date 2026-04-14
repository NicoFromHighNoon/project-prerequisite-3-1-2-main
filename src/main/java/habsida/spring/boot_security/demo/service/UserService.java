package habsida.spring.boot_security.demo.service;


import habsida.spring.boot_security.demo.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsersList();
    void addUser(User user);
    void updateUser(User user);
    void removeUser(Long id);
    User findUser(Long id);
    Optional<User> findByUsername(String username);
}
