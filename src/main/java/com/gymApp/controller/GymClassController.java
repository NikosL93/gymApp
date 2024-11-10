package com.gymApp.controller;

import com.gymApp.payload.GymClassResponse;
import com.gymApp.payload.GymClassDTO;
import com.gymApp.service.GymClassService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class GymClassController {
    private GymClassService gymClassService;

    public GymClassController(GymClassService gymClassService) {
        this.gymClassService = gymClassService;
    }
    @Operation(summary = "Get all gym classes")
    @GetMapping("api/public/gymClasses")
    public ResponseEntity<GymClassResponse> getAllGymClasses(){
        GymClassResponse gymClassResponse = gymClassService.getAllGymClasses();
        return new ResponseEntity<>(gymClassResponse, HttpStatus.OK);
    }
    @Operation(summary = "Create a gym class")
    @PostMapping("api/admin/gymClasses")
    public ResponseEntity<GymClassDTO> createGymClass(@Valid @RequestBody GymClassDTO gymClassDTO) {
        GymClassDTO savedGymClassDTO = gymClassService.createGymClass(gymClassDTO);
        return new ResponseEntity<>(savedGymClassDTO, HttpStatus.CREATED);
    }
    @Operation(summary = "Update a gym class by ID")
    @PutMapping("api/admin/gymClasses/{gymClassId}")
    public ResponseEntity<GymClassDTO> updateGymClass(@Valid @RequestBody GymClassDTO gymClassDTO, @PathVariable Long gymClassId) {
        GymClassDTO updatedGymClass = gymClassService.updateGymClass(gymClassDTO, gymClassId);
        return new ResponseEntity<>(updatedGymClass, HttpStatus.OK);
    }
    @Operation(summary = "Delete a gym class by ID")
    @DeleteMapping("api/admin/gymClasses/{gymClassId}")
    public ResponseEntity<GymClassDTO> deleteGymClass(@PathVariable Long gymClassId) {
        GymClassDTO deletedGymClass = gymClassService.deleteGymClass(gymClassId);
        return new ResponseEntity<>(deletedGymClass, HttpStatus.OK);
    }
    @Operation(summary = "Upload a gym class Image")
    @PutMapping("api/admin/gymClasses/{gymClassId}/image")
    public ResponseEntity<GymClassDTO> updateGymClassImage(@PathVariable Long gymClassId, @RequestParam("image") MultipartFile image) throws IOException {
        GymClassDTO updatedGymClass = gymClassService.updateGymClassImage(gymClassId, image);
        return new ResponseEntity<>(updatedGymClass, HttpStatus.OK);
    }

}
