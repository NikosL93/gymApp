package com.gymApp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File names του original file
        String originalFileName = file.getOriginalFilename(); //το filename μαζί με το .extension
        //Generate unique file name για να μην έχω conflict με ήδη υπάρχων file
        String randomId = UUID.randomUUID().toString();
        //example.jpg --> 1234 --> 1234.jpg
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        String filePath = path + File.separator + fileName; // File.Separator = /
        //Έλεγχος αν υπάρχει το path(folder) με όνομα images και δημιουργία
        File folder = new File(path);
        if(!folder.exists())
            folder.mkdir();
        //Copy του file(image) στο path που δημιουργήσαμε
        Files.copy(file.getInputStream(), Paths.get(filePath)); //H copy και δημιουργεί ένα κενό file και μεταφέρει τα δεδομένα
        //Return file name
        return fileName;
    }
}
