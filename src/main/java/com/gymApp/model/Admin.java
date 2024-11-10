package com.gymApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;
    private String adminName;
    @NotBlank
    @Size(max=50)
    @Column(name = "username", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;
    
    @NotBlank
    @Email
    @Size(max=150)
    @Column(name = "email", unique = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL) //Το cascade πάντα στο owner entity
    @JoinColumn(name = "user_id")
    private User user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.ROLE_ADMIN;

    public Admin(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}