package com.gymApp.service;
import com.gymApp.exceptions.ApiException;
import com.gymApp.exceptions.ResourceNotFoundException;
import com.gymApp.model.Admin;
import com.gymApp.model.Member;
import com.gymApp.model.Role;
import com.gymApp.model.User;
import com.gymApp.payload.AdminDTO;
import com.gymApp.payload.AdminResponse;
import com.gymApp.repositories.AdminRepository;
import com.gymApp.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AdminResponse getAllAdmins(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Admin> adminPage = adminRepository.findAll(pageable);
        List<Admin> admins = adminPage.getContent();
        if (admins.isEmpty()) {
            throw new ApiException("No admins created till now!");
        }
        List<AdminDTO> adminDTOS = admins.stream()
                .map(admin -> modelMapper.map(admin, AdminDTO.class))
                .toList();
                
        AdminResponse adminResponse = new AdminResponse();
        adminResponse.setContent(adminDTOS);
        adminResponse.setPageNumber(adminPage.getNumber());
        adminResponse.setPageSize(adminPage.getSize());
        adminResponse.setTotalElements(adminPage.getTotalElements());
        adminResponse.setTotalPages(adminPage.getTotalPages());
        adminResponse.setLastPage(adminPage.isLast());
        
        return adminResponse;
    }

    @Override
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        // Έλεγχος αν υπάρχει ήδη το username
        if(adminRepository.existsByUserName(adminDTO.getUserName())) {
            throw new ApiException("Username already exists!");
        }
        
        // Έλεγχος αν υπάρχει ήδη το email
        if(adminRepository.existsByEmail(adminDTO.getEmail())) {
            throw new ApiException("Email already exists!");
        }
        // Δημιουργώ τον αντίστοιχο User
        User user = new User();
        user.setUserName(adminDTO.getUserName());
        user.setEmail(adminDTO.getEmail());
        user.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        user.setRole(Role.ROLE_ADMIN);
        //Δημιουργώ τον admin
        Admin admin =  modelMapper.map(adminDTO, Admin.class);
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        //Αντιστοίχιση με user
        admin.setUser(user);
        admin.setRole(Role.ROLE_ADMIN);
        //Το σώζω στην DB
        Admin savedAdmin = adminRepository.save(admin);
        return modelMapper.map(savedAdmin, AdminDTO.class);
    }

    @Override
    public AdminDTO deleteAdmin(Long adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin", "adminId", adminId));
        adminRepository.delete(admin);
        return modelMapper.map(admin, AdminDTO.class);
    }

    @Override
    public AdminDTO updateAdmin(AdminDTO adminDTO, Long adminId) {
        Admin existingAdmin = adminRepository.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin", "adminId", adminId));
                
        // Check if username is taken by another admin
        if(!existingAdmin.getUserName().equals(adminDTO.getUserName()) && 
           adminRepository.existsByUserName(adminDTO.getUserName())) {
            throw new ApiException("Username already exists!");
        }
        
        // Check if email is taken by another admin
        if(!existingAdmin.getEmail().equals(adminDTO.getEmail()) && 
           adminRepository.existsByEmail(adminDTO.getEmail())) {
            throw new ApiException("Email already exists!");
        }
        
        Admin admin = modelMapper.map(adminDTO, Admin.class);
        admin.setAdminId(adminId);
        admin.setRole(Role.ROLE_ADMIN);
        
        Admin updatedAdmin = adminRepository.save(admin);
        return modelMapper.map(updatedAdmin, AdminDTO.class);
    }

    @Override
    public AdminResponse searchAdminByName(String name) {
        List<Admin> admins = adminRepository.findByAdminNameLikeIgnoreCase("%" + name + "%");
        List<AdminDTO> adminDTOS = admins.stream()
                .map(admin -> modelMapper.map(admin, AdminDTO.class))
                .toList();
                
        AdminResponse adminResponse = new AdminResponse();
        adminResponse.setContent(adminDTOS);
        return adminResponse;
    }
} 