package com.gymApp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gymApp.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    private Long adminId;
    
    @NotBlank
    private String adminName;
    
    @NotBlank
    @Size(max=50)
    private String userName;
    
    @NotBlank
    private String password;
    
    @NotBlank
    @Email
    @Size(max=150)
    private String email;
    private Role role = Role.ROLE_ADMIN;

} 