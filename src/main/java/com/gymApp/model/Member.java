package com.gymApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //Χρησιμοποιώ το Lombok για να δημιουργήσει αυτόματα τα getters/setters/constructors
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    //H serialize σε json γίνεται με getters/setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String memberName;

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

    // Κάθε μέλος έχει δικαίωμα συμμετοχής σε μία μόνο τάξη
    @ManyToOne
    @JoinColumn(name= "gymClass_Id")
    @JsonIgnore
    private GymClass gymClass;

    @OneToOne(cascade = CascadeType.ALL) //Το cascade πάντα στο owner entity
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.ROLE_MEMBER;

    public Member(String userName,  String email, String password) {
        this.userName = userName;
        this.password = password;
        this.email = email;

    }
}


