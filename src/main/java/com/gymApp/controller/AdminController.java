package com.gymApp.controller;

import com.gymApp.config.AppConstants;
import com.gymApp.payload.AdminDTO;
import com.gymApp.payload.AdminResponse;
import com.gymApp.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Get all Admins")
    @GetMapping("api/admin/admins")
    public ResponseEntity<AdminResponse> getAllAdmins(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = "adminName", required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        AdminResponse adminResponse = adminService.getAllAdmins(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(adminResponse, HttpStatus.OK);
    }
    @Operation(summary = "Create an Admin")
    @PostMapping("api/admin/admins")
    public ResponseEntity<AdminDTO> createAdmin(@Valid @RequestBody AdminDTO adminDTO) {
        AdminDTO savedAdminDTO = adminService.createAdmin(adminDTO);
        return new ResponseEntity<>(savedAdminDTO, HttpStatus.CREATED);
    }
    @Operation(summary= "Delete an Admin by ID")
    @DeleteMapping("api/admin/admins/{adminId}")
    public ResponseEntity<AdminDTO> deleteAdmin(@PathVariable Long adminId) {
        AdminDTO deletedAdmin = adminService.deleteAdmin(adminId);
        return new ResponseEntity<>(deletedAdmin, HttpStatus.OK);
    }
    @Operation(summary = "Update an Admin by ID")
    @PutMapping("api/admin/admins/{adminId}")
    public ResponseEntity<AdminDTO> updateAdmin(@Valid @RequestBody AdminDTO adminDTO, @PathVariable Long adminId) {
        AdminDTO updatedAdmin = adminService.updateAdmin(adminDTO, adminId);
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }
    @Operation(summary = "Get/search Admin by name")
    @GetMapping("api/admin/admins/name/{name}")
    public ResponseEntity<AdminResponse> getAdminByName(@PathVariable String name) {
        AdminResponse foundAdmins = adminService.searchAdminByName(name);
        return new ResponseEntity<>(foundAdmins, HttpStatus.OK);
    }
} 