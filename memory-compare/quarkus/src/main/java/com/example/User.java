package com.example;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {
    
    @Column(nullable = false)
    public String name;
    
    @Column(nullable = false, unique = true)
    public String email;
    
    @Column
    public String phone;
    
    public User() {}
    
    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
    public static User findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
