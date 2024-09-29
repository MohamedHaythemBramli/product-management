package com.product.productmanagement.web;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {



    @PostMapping("/csv")
    public String uploadCSV(@RequestParam("file") MultipartFile file) {
        // Validate the file type
        if (!file.getContentType().equals("text/csv")) {
            return "Invalid file type. Please upload a CSV file.";
        }

        // Define the path to the resources folder (you can customize this)
        String resourcePath = "C:\\Users\\User\\Desktop\\day\\product-management\\src\\main\\resources";
        File directory = new File(resourcePath);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }

        // Define the path where the file will be saved
        File csvFile = new File(directory, file.getOriginalFilename());

        // Save the file
        try {
            file.transferTo(csvFile);
            return "File uploaded successfully: " + csvFile.getAbsolutePath();
        } catch (IOException e) {
            return "Failed to upload file: " + e.getMessage();
        }
    }
}

