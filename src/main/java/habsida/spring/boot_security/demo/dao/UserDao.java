package habsida.spring.boot_security.demo.dao;

import habsida.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    public List<User> getUsersList();
    public void addUser(User user);
    public void updateUser(User user);
    public void removeUser(Long id);
    public User findUser(Long id);
    Optional<User> findByUsername(String username);
}
