package com.gymApp.payload;

import com.gymApp.model.GymClass;
import com.gymApp.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long memberId;
    @NotBlank
    private String memberName;
    @NotBlank
    @Size(max=50)
    private String userName;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    @Size(max=150)
    private String email;
    private GymClass gymClass;
    private Role role = Role.ROLE_MEMBER;
}
