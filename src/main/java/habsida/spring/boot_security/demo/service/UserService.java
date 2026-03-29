package habsida.spring.boot_security.demo.service;


import habsida.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    public List<User> getUsersList();
    public void addUser(User user);
    public void updateUser(User user);
    public void removeUser(Long id);
    public User findUser(Long id);
}
