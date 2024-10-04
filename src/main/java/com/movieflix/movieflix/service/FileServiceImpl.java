package com.movieflix.movieflix.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        // Get the file name
        String fileName = file.getOriginalFilename();

        // Get the file path
        String filePath = path + File.separator + fileName;

        // Create the directory if it doesn't exist
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Copy the file or upload the file
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;
        File file = new File(filePath);
        
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }

        return new FileInputStream(file);
    }
}
