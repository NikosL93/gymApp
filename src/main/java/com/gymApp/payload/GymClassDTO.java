package com.gymApp.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gymApp.model.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GymClassDTO {
    private Long gymClassId;
    private String gymClassName;
    private String gymClassImage;
    private String gymClassDate;
    @JsonIgnore
    private List<Member> members = new ArrayList<>();
}
