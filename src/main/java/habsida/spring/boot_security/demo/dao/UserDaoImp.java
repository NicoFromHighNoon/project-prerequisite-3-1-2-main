package habsida.spring.boot_security.demo.dao;

import habsida.spring.boot_security.demo.model.User;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getUsersList() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void removeUser(Long id) {
        User user = findUser(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public User findUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(entityManager.find(User.class, username));
    }
}
