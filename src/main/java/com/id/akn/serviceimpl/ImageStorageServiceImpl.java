package com.id.akn.serviceimpl;

import com.id.akn.service.ImageStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {
    private final String baseDir = System.getProperty("user.dir") + "/src/main/resources/static/images/";
    private final String laptopDir = baseDir + "laptop/";
    private final String homeSlideDir = baseDir + "homeslide/";
    private final String blogDir = baseDir + "blog/";

    @Override
    public Set<String> saveFiles(Integer laptopId, Set<MultipartFile> files) throws IOException {
        Set<String> imageUrls = new HashSet<>();

        Path laptopDirPath = Paths.get(laptopDir + laptopId);
        if (!Files.exists(laptopDirPath)) {
            Files.createDirectories(laptopDirPath);
        }

        for (MultipartFile file : files) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = laptopDirPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String imageUrl = "/images/laptop/" + laptopId + "/" + fileName;
            imageUrls.add(imageUrl);
        }
        return imageUrls;
    }

    @Override
    public void deleteFiles(Integer laptopId, Set<String> imageUrls) throws IOException {
        Path laptopDirPath = Paths.get(laptopDir + laptopId);
        for (String imageUrl : imageUrls) {
            String fileName = Paths.get(imageUrl).getFileName().toString();
            Path filePath = laptopDirPath.resolve(fileName);
            Files.deleteIfExists(filePath);
        }
    }

    @Override
    public void deleteLaptopDirectory(Integer laptopId) throws IOException {
        Path laptopDirPath = Paths.get(laptopDir + laptopId);
        if (Files.exists(laptopDirPath)) {
            try (var paths = Files.walk(laptopDirPath).sorted(Comparator.reverseOrder())) {
                paths.forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    @Override
    public List<String> getHomeSlideImages() throws IOException {
        Path homeSlideDirPath = Paths.get(homeSlideDir);
        if (!Files.exists(homeSlideDirPath)) {
            return Collections.emptyList();
        }
        return Files.list(homeSlideDirPath)
                .filter(Files::isRegularFile)
                .map(path -> "/images/homeslide/" + path.getFileName().toString())
                .collect(Collectors.toList());
    }

    @Override
    public Boolean deleteHomeSlideImage(String url) throws IOException {
        Path baseDirPath = Paths.get(homeSlideDir).toAbsolutePath();
        Path resolvedPath = baseDirPath.resolve(url.replace("/images/homeslide/", "")).toAbsolutePath();

        Path normalizedPath = resolvedPath.normalize();
        if (!normalizedPath.startsWith(baseDirPath)) {
            throw new IOException("Access denied");
        }

        if (Files.exists(normalizedPath)) {
            try {
                return Files.deleteIfExists(normalizedPath);
            } catch (IOException e) {
                throw new IOException(e);
            }
        }
        return false;
    }

    @Override
    public String saveHomeSlideImage(MultipartFile file) throws IOException {
        Path homeSlideDirPath = Paths.get(homeSlideDir);
        if (!Files.exists(homeSlideDirPath)) {
            Files.createDirectories(homeSlideDirPath);
        }
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = homeSlideDirPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        String homeSlideUrl = "/images/homeslide/" + fileName;
        return homeSlideUrl;
    }

    @Override
    public String saveBlogImage(MultipartFile file) throws IOException {
        Path blogDirPath = Paths.get(blogDir);
        if (!Files.exists(blogDirPath)) {
            Files.createDirectories(blogDirPath);
        }
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = blogDirPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "/images/blog/" + fileName;
    }

    @Override
    public void deleteBlogImage(String url) throws IOException {
        if (url == null || url.isEmpty())
            return;
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        Path filePath = Paths.get(blogDir).resolve(fileName);
        Files.deleteIfExists(filePath);
    }
}

// [FAKE-UPDATE] Thay đổi nội dung để cập nhật git history
//  Thay đổi nội dung để cập nhật git history