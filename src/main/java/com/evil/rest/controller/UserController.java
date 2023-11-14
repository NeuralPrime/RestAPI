package com.evil.rest.controller;

import com.evil.rest.model.User;
import com.evil.rest.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    private UserRepository userRepository;
    private String code = "";

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/execute")
    public void executeOperations() {
        List<User> users = userRepository.getAllUsers();

        if (users != null && !users.isEmpty()) {
            User user = new User();
            user.setId(3);
            user.setName("James");
            user.setLastName("Brown");
            user.setAge((byte) 30);

            User createdUser = userRepository.createUser(user);
            if (createdUser != null) {
                code += createdUser.getId();

                User updatedUser = new User();
                updatedUser.setName("Thomas");
                updatedUser.setLastName("Shelby");

                User modifiedUser = userRepository.updateUser(createdUser.getId(), updatedUser);
                if (modifiedUser != null) {
                    code += modifiedUser.getName().substring(1, 3);

                    userRepository.deleteUser(modifiedUser.getId());
                    code += modifiedUser.getName().substring(4);
                }
            }
        }
    }

    @GetMapping("/code")
    public String getCode() {
        return code;
    }


}