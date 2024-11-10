package com.gymApp.service;

import com.gymApp.exceptions.ResourceNotFoundException;
import com.gymApp.model.GymClass;
import com.gymApp.payload.GymClassDTO;
import com.gymApp.payload.GymClassResponse;
import com.gymApp.repositories.GymClassRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class GymClassServiceImpl implements GymClassService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GymClassRepository gymClassRepository;
    @Autowired
    private FileService fileService;

    @Override
    public GymClassDTO createGymClass(GymClassDTO gymClassDTO) {
        GymClass gymClass =  modelMapper.map(gymClassDTO, GymClass.class);
        GymClass savedGymClass = gymClassRepository.save(gymClass);
        return modelMapper.map(savedGymClass, GymClassDTO.class);
    }
    @Override
    public GymClassResponse getAllGymClasses() {
        List<GymClass> gymClasses = gymClassRepository.findAll();
        List<GymClassDTO> gymClassDTOS = gymClasses.stream()
                .map(gymClass -> modelMapper.map(gymClass, GymClassDTO.class))
                .toList();
        GymClassResponse gymClassResponse = new GymClassResponse();
        gymClassResponse.setContent(gymClassDTOS);
        return gymClassResponse;
    }
    @Override
    public GymClassDTO updateGymClass(GymClassDTO gymClassDTO, Long gymClassId) {
        //Παίρνω το gymClass από DB
        Optional<GymClass> savedGymClassOptional = gymClassRepository.findById(gymClassId);
        GymClass savedGymClass = savedGymClassOptional.orElseThrow(  //Έλεγχος αν υπάρχει
                () -> new ResourceNotFoundException("gymClass", "gymClassId", gymClassId));
        GymClass gymClass = modelMapper.map(gymClassDTO, GymClass.class);
        //Ενημερώνω το gymClassId του δοθέντος gymClass μ' αυτό που βρήκε στην DB ώστε να αντικατασταθεί
        gymClass.setGymClassId(gymClassId);
        //Το σώζω στην DB
        GymClass updatedGymClass = gymClassRepository.save(gymClass);
        return modelMapper.map(updatedGymClass, GymClassDTO.class);
    }
    @Override
    public GymClassDTO deleteGymClass(Long gymClassId) {
        //Παίρνω το gymClass από DB
        Optional<GymClass> savedGymClassOptional = gymClassRepository.findById(gymClassId);
        GymClass savedGymClass = savedGymClassOptional
                .orElseThrow(()->new ResourceNotFoundException("GymClass", "GymClassId", gymClassId)); // H orElseThrow επενεργεί πάνω στο Optional αντικ savedGymClassOptional και επιστρέφει την τιμή του (αντικ GymClass) αν υπάρχει
        //Το διαγράφω απ την DB
        gymClassRepository.delete(savedGymClass);
        return modelMapper.map(savedGymClass, GymClassDTO.class);
    }

    @Override
    public GymClassDTO updateGymClassImage(Long gymClassId, MultipartFile image) throws IOException {
        //Παίρνω το gymClass από DB
        Optional<GymClass> savedGymClassOptional = gymClassRepository.findById(gymClassId);
        GymClass savedGymClass = savedGymClassOptional
                .orElseThrow(()->new ResourceNotFoundException("GymClass", "GymClassId", gymClassId));
        //Upload image
        //Παίρνω το όνομα της uploaded image
        String path = "images/";
        String filename = fileService.uploadImage(path, image);
        //Ενημερώνω το νέο file name στο savedGymClass
        savedGymClass.setGymClassImage(filename);
        //Το σώζω στην DB
        GymClass updatedGymClass = gymClassRepository.save(savedGymClass);
        return modelMapper.map(updatedGymClass, GymClassDTO.class);
    }

}