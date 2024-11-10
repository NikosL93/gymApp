package com.gymApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GymClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gymClassId;
    private String gymClassName;
    private String gymClassImage;
    private String gymClassDateTime;

    @OneToMany(mappedBy = "gymClass")
    @JsonIgnore
    @ToString.Exclude
    private List<Member> members = new ArrayList<>();

}
