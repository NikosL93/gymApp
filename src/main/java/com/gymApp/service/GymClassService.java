package com.gymApp.service;

import com.gymApp.payload.GymClassDTO;
import com.gymApp.payload.GymClassResponse;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface GymClassService {
    GymClassDTO createGymClass(GymClassDTO gymClassDTO);
    GymClassResponse getAllGymClasses();
    GymClassDTO updateGymClass(@Valid GymClassDTO gymClassDTO, Long gymClassId);
    GymClassDTO deleteGymClass(Long gymClassId);
    GymClassDTO updateGymClassImage(Long gymClassId, MultipartFile image) throws IOException;
}
