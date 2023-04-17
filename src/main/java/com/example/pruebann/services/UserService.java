package com.example.pruebann.services;
import com.example.pruebann.model.User;

import java.util.List;
public interface UserService {
    List<User> getUser();

    User getUserById(Long id);
    User insert(User user);
    
    void updateUser(Long id, User user);
    void deleteUser(Long userId);
    
    User  getUserbyEmail(String email);  

}