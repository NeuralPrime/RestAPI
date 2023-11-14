package com.evil.rest.repository;

import com.evil.rest.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(int id, User user);

    void deleteUser(int id);
}
