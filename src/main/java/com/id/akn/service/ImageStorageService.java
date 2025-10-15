package com.id.akn.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface ImageStorageService {
    Set<String> saveFiles(Integer productId, Set<MultipartFile> files) throws IOException;

    void deleteFiles(Integer laptopId, Set<String> imageUrls) throws IOException;
ages() throws IOException;

    Boolean deleteHomeSlideImage(String url) throws IOException;

    String saveHomeSlideImage(MultipartFile file) throws IOException;

    String saveBlogImage(MultipartFile file) throws IOException;

    void deleteBlogImage(String url) throws IOException;
}
