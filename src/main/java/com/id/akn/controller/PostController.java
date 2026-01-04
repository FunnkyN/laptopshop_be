package com.id.akn.controller;

import com.id.akn.exception.PostException;
import com.id.akn.exception.UserException;
import com.id.akn.model.User;
import com.id.akn.request.PostDTO;
import com.id.akn.service.PostService;
import com.id.akn.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/posts/latest")
    public ResponseEntity<List<PostDTO>> getLatestPosts(@RequestParam(defaultValue = "4") int limit) {
        return ResponseEntity.ok(postService.getLatestPosts(limit));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) throws PostException {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping(value = "/admin/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDTO> createPost(
            @RequestPart("post") PostDTO postDTO,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestHeader("Authorization") String jwt
    ) throws UserException, IOException {
        User user = userService.findUserProfileByJwt(jwt);
        PostDTO createdPost = postService.createPost(postDTO, file, user);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping(value = "/admin/posts/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostDTO> updatePost(
            @PathVariable Long id,
            @RequestPart("post") PostDTO postDTO,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestHeader("Authorization") String jwt
    ) throws PostException, IOException {

        PostDTO updatedPost = postService.updatePost(id, postDTO, file);
        return ResponseEntity.ok(updatedPost);
    }

}

//  Thay đổi nội dung để cập nhật git history