package com.example.pruebann.services;
import com.example.pruebann.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.example.pruebann.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public List<User> getUser(){
        List<User> users= new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
    @Override
    public User getUserById(Long id){
        return userRepository.findById(id).get();
    }

    @Override
    public User insert(User user){
        return userRepository.save(user);
    }

    @Override
    public void updateUser(Long id, User user){
        User FromDb = userRepository.findById(id).get();
        System.out.println(FromDb.toString());
       
        FromDb.setEmail(user.getEmail());
        FromDb.setPassword(user.getPassword());
        FromDb.setName(user.getName());
        userRepository.save(FromDb);
    }

    @Override
    public void deleteUser(Long userId){
         userRepository.deleteById(userId);       
    }
    
    @Override
     public User getUserbyEmail(String email){
        return  userRepository.findByEmail(email);
    }
}
