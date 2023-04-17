package com.example.pruebann.controllers;
import com.example.pruebann.model.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.regex.Pattern;
import java.util.List;
import com.example.pruebann.services.UserService;   
 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import com.example.pruebann.services.JwtUtils;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends JwtUtils  {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    JwtUtils jwtUtils;
    UserService userService;
    
      
    public UserController(UserService userService ){
        this.userService = userService;        
    }
 
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllUser(){
         logger.info("GET getAllUser  log message. Logged at: " + new Date().toString());
          Map<String, Object> response = new HashMap<>();
        try {
            
            List<User> users= userService.getUser(); 
            logger.trace("Request body: {}", users); 
            if(!users.isEmpty() ){          
             response.put("data", users);    
            }else {               
             response.put("mensaje", "No existe data");
             }     
                
          return ResponseEntity.status(HttpStatus.OK).body(response); 
         } catch (Exception e) {
              logger.error("Fail en el metodo getAllUser " + e.getMessage());
           response.put("mensaje", e.getMessage());
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); 
    }
    }

    @GetMapping({"/{userId}"})
   public ResponseEntity<Map<String, Object>> getUser(@PathVariable Long userId){
         logger.info("GET getUser  log message. Logged at: " + new Date().toString());
           Map<String, Object> response = new HashMap<>();
        try {
           User user= userService.getUserById(userId);
           logger.debug("getUser from Logback {}", user);
            response.put("data", user);            
          return ResponseEntity.status(HttpStatus.OK).body(response);          
         } catch (Exception e) {
            logger.error("Fail en el metodo getUser " + e.getMessage());             
           response.put("mensaje", e.getMessage());
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); 
         }       
    }

    @PostMapping()
    public ResponseEntity<Object> saveUser(@RequestBody  User user){
       
        logger.info("POST saveUser log message. Logged at: " + new Date().toString());
   
         Map<String, String> response = new HashMap<>();
        try { 
           System.out.println(user.getEmail());
           System.out.println(user.getPassword());
        // Validar una direccion de correo electronico
        if (!emailValidator(user.getEmail())) { 
           response.put("mensaje", "Email no cumple con las reglas");
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
        }   
        if (!passwordValidator(user.getPassword())) { 
           response.put("mensaje", "Password no cumple con las reglas");
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  
        } 
        User userEmail = userService.getUserbyEmail(user.getEmail());
        if (userEmail != null) {   
           response.put("mensaje", "Email ya existe");
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  
        }  
         logger.trace("Request body Save: {}", user);
         String token =  generateJwtToken(user.getEmail());
         
         user.setToken(token); 
         User save = userService.insert(user);     
         
          
        response.put("id", user.getId().toString());        
        response.put("created", save.getDateCreated().toString());
        response.put("modified", save.getLastModified().toString()); 
        response.put("token", token);
         return ResponseEntity.status(HttpStatus.CREATED).body(response);
          } catch (Exception e) {
          logger.error("Fail en el metodo saveUser " + e.getMessage());
         response.put("mensaje", e.getMessage());
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); 
          
         }       
    }
    
    @PutMapping({"/{userId}"}) 
    public ResponseEntity<Object> updateUser(@PathVariable("userId") Long userId, @RequestBody User user){
      logger.info("PUT updateUser log message. Logged at: " + new Date().toString());
        Map<String, String> response = new HashMap<>();
        try {         
        logger.trace("Request body Update: {}", user);
        if (!emailValidator(user.getEmail())) { 
           response.put("mensaje", "Email no cumple con las reglas");
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); 
        }   
        if (!passwordValidator(user.getPassword())) { 
           response.put("mensaje", "Password no cumple con las reglas");
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  
        } 
         String token =  generateJwtToken(user.getEmail());
         System.out.println ("nuevo tolen" + token);
        user.setId(userId);
        user.setToken(token); 
        logger.trace("Generate new token: ", token);
        userService.updateUser(userId, user);       
             
         User userUpdate= userService.getUserById(userId);
        response.put("id", userUpdate.getId().toString());        
        response.put("created", userUpdate.getDateCreated().toString());
        response.put("modified", userUpdate.getLastModified().toString()); 
        response.put("token", token);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
          
        } catch (Exception e) {
         logger.error("Fail en el metodo updateUser " + e.getMessage());
             
           response.put("mensaje", e.getMessage());
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);     
        }        
    }

    @DeleteMapping({"/{userId}"}) 
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable("userId") Long userId){     
        logger.info("DELETE deleteUser log message. Logged at: " + new Date().toString());
        Map<String, String> response = new HashMap<>();
        try {   
            userService.deleteUser(userId);    
            response.put("mensaje", "usuario eliminado correctamente");
            return ResponseEntity.ok().body(response);
          
        } catch (Exception e) {
            logger.error("Fail en el metodo deleteUser " + e.getMessage());        
            response.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);       
       }
    }
    
    public static boolean emailValidator(String email) {
     Pattern regexPattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        if (email == null) {
            return false;
        }
        return regexPattern.matcher(email)
          .matches();
    }  
     public static boolean passwordValidator(String password) {   
        Pattern regexPattern = Pattern
                .compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9].*[0-9]).{8,}$");
        if (password == null) {
            return false;
        } 
        return regexPattern.matcher(password)
          .matches();        
    }    
     
}
