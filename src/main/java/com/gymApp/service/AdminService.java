package com.gymApp.service;

import com.gymApp.payload.AdminDTO;
import com.gymApp.payload.AdminResponse;

public interface AdminService {
    AdminResponse getAllAdmins(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    AdminDTO createAdmin(AdminDTO adminDTO);
    AdminDTO deleteAdmin(Long adminId);
    AdminDTO updateAdmin(AdminDTO adminDTO, Long adminId);
    AdminResponse searchAdminByName(String name);
} 