package com.id.akn.controller;

import com.id.akn.service.ImageStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/banner")
@AllArgsConstructor
public class BannerController {
    private ImageStorageService imageStorageService;

    @GetMapping("/slideimage")
    public ResponseEntity<List<String>> getHomeSlideImages() throws IOException {
        List<String> homeSlideImages = imageStorageService.getHomeSlideImages();
        return new ResponseEntity<>(homeSlideImages, HttpStatus.OK);
    }
}
