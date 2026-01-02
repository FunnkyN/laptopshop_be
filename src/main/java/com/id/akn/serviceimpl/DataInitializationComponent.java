package com.id.akn.serviceimpl;

import com.id.akn.model.User;
import com.id.akn.repository.UserRepository;
import com.id.akn.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class DataInitializationComponent implements CommandLineRunner {

    private final UserRepository userRepository;
    private CartService cartService;
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        try {
            initializeImageFolders();
            initializeAdminUser();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeImageFolders() throws IOException {
        String baseDir = System.getProperty("user.dir") + "/src/main/resources/static/images/";
        Path imagesDir = Paths.get(baseDir);
        Path laptopDir = Paths.get(baseDir + "laptop/");
        Path homeSlideDir = Paths.get(baseDir + "homeslide/");

        if (!Files.exists(imagesDir)) {
            Files.createDirectories(imagesDir);
        }
        if (!Files.exists(laptopDir)) {
            Files.createDirectories(laptopDir);
        }
        if (!Files.exists(homeSlideDir)) {
            Files.createDirectories(homeSlideDir);
        }
    }

    private void initializeAdminUser() {
        String adminUsername = "admin@gmail.com";

        if (userRepository.findByEmail(adminUsername) == null) {
            User adminUser = new User();

            adminUser.setPassword(passwordEncoder.encode("123123123"));
            adminUser.setName("Shop Admin");
            adminUser.setEmail("admin@gmail.com");
            adminUser.setPhoneNumber("0362501803");
            adminUser.setRole(User.Role.ADMIN);
            adminUser.setCreatedAt(LocalDateTime.now());
            User admin = userRepository.save(adminUser);
            cartService.createCart(admin);
        }
    }
}
