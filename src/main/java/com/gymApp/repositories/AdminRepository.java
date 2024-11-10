package com.gymApp.repositories;

import com.gymApp.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    List<Admin> findByAdminNameLikeIgnoreCase(String name);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
} 