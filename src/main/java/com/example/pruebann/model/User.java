package com.example.pruebann.model;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id; 
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
 
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
  
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    Long id;
    @Column
    @NotNull        
    private String name;   
    @Column(nullable=false, length=120, unique = true)
    @NotNull 
    private String email; 
    @NotNull
    private String password;  
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<UserPhone> phones;
    
    
    @Column(nullable=true)
    private String Token;

    @CreationTimestamp
    @Column(updatable = false)
    Timestamp dateCreated;
    @UpdateTimestamp
    Timestamp lastModified;

   

}