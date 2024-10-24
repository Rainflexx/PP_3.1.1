package ru.rainflexx.springboot.dao;

import org.springframework.stereotype.Repository;
import ru.rainflexx.springboot.models.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository

public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void adduser(User user) {
        user.validateUserData(user);
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }else {
            throw new EntityNotFoundException("User not founded");
        }
    }

    @Override
    public void updateUser(User user) {
        if (user != null) {
            entityManager.merge(user);
        } else {
            throw new EntityNotFoundException("User not founded");
        }
    }

    @Override
    public User getUser(int id) {
        if (entityManager.find(User.class, id) != null) {
            return entityManager.find(User.class, id);
        } else {
            throw new EntityNotFoundException("User not founded");
        }
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }
}
