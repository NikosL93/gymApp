package com.gymApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @NotBlank
    @Size(max=50)
    @Column(name = "username", unique = true)
    private String userName;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank
    @Email
    @Size(max=150)
    @Column(name = "email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user")
    private Member member;

    @OneToOne(mappedBy = "user")
    private Admin admin;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
}