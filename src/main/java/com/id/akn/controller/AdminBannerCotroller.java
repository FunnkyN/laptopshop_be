package com.id.akn.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.id.akn.service.ImageStorageService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/admin/banner")
@AllArgsConstructor
public class AdminBannerCotroller {
    private ImageStorageService imageStorageService;

    @DeleteMapping("/{filename:.+}")
    public ResponseEntity<String> deleteFile(@PathVariable String filename) throws IOException {
        boolean deleted = imageStorageService.deleteHomeSlideImage(filename);
        if (deleted) 

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException{
        String filename = imageStorageService.saveHomeSlideImage(file);
        return new ResponseEntity<>(filename, HttpStatus.OK);
    }
}
