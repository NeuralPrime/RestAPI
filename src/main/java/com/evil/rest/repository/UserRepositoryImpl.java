package com.evil.rest.repository;


import com.evil.rest.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private RestTemplate restTemplate;
    private String sessionId;

    public UserRepositoryImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<User> getAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        if (sessionId != null) {
            headers.add("Cookie", sessionId);
        }
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<List<User>> response = restTemplate.exchange("http://94.198.50.185:7081/api/users", HttpMethod.GET, entity, new ParameterizedTypeReference<List<User>>() {});
        List<User> users = response.getBody();

        if (sessionId == null) {
            List<String> cookies = response.getHeaders().get("Set-Cookie");
            if (cookies != null) {
                for (String cookie : cookies) {
                    if (cookie.startsWith("session=")) {
                        sessionId = cookie.split(";")[0];
                    }
                }
            }
        }

        return users;
    }

    @Override
    public User createUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        if (sessionId != null) {
            headers.add("Cookie", sessionId);
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<User> response = restTemplate.postForEntity("http://94.198.50.185:7081/api/users", request, User.class);
        User createdUser = response.getBody();

        return createdUser;
    }

    @Override
    public User updateUser(int id, User user) {
        HttpHeaders headers = new HttpHeaders();
        if (sessionId != null) {
            headers.add("Cookie", sessionId);
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<User> response = restTemplate.exchange("http://94.198.50.185:7081/api/users/" + id, HttpMethod.PUT, request, User.class);
        User updatedUser = response.getBody();

        return updatedUser;
    }

    @Override
    public void deleteUser(int id) {
        HttpHeaders headers = new HttpHeaders();
        if (sessionId != null) {
            headers.add("Cookie", sessionId);
        }
        HttpEntity<?> entity = new HttpEntity<>(headers);
        restTemplate.exchange("http://94.198.50.185:7081/api/users/" + id, HttpMethod.DELETE, entity, Void.class);
    }
}
