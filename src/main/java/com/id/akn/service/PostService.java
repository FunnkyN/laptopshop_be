package com.id.akn.service;

import com.id.akn.exception.PostException;
import com.id.akn.model.User;
import com.id.akn.request.PostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO, MultipartFile file, User user) throws IOException;
    PostDTO updatePost(Long id, PostDTO postDTO, MultipartFile file) throws PostException, IOException;
    void deletePost(Long id) throws PostException, IOException;
    PostDTO getPostById(Long id) throws PostException;
    List<PostDTO> getAllPosts();
    List<PostDTO> getLatestPosts(int limit);
}
