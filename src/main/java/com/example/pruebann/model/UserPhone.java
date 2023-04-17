package com.example.pruebann.model;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder; 

import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; 
import javax.persistence.Table;
 
@Entity
@Table(name = "phones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
 

public class UserPhone {

    @Id    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    private String number;

} 
 