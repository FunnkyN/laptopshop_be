package com.id.akn.serviceimpl;

import com.id.akn.exception.PostException;
import com.id.akn.model.Post;
import com.id.akn.model.User;
import com.id.akn.repository.PostRepository;
import com.id.akn.request.PostDTO;
import com.id.akn.service.ImageStorageService;
import com.id.akn.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ImageStorageService imageStorageService;

    @Override
    public PostDTO createPost(PostDTO postDTO, MultipartFile file, User user) throws IOException {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setShortDescription(postDTO.getShortDescription());
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        if (file != null && !file.isEmpty()) {
            String imageUrl = imageStorageService.saveBlogImage(file);
            post.setThumbnail(imageUrl);
        }

        Post savedPost = postRepository.save(post);
        return convertToDTO(savedPost);
    }

    @Override
    public PostDTO updatePost(Long id, PostDTO postDTO, MultipartFile file) throws PostException, IOException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException("Không tìm thấy bài viết với ID: " + id));

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setShortDescription(postDTO.getShortDescription());
        post.setUpdatedAt(LocalDateTime.now());

        if (file != null && !file.isEmpty()) {

            if (post.getThumbnail() != null) {
                imageStorageService.deleteBlogImage(post.getThumbnail());
            }

            String imageUrl = imageStorageService.saveBlogImage(file);
            post.setThumbnail(imageUrl);
        }

        Post updatedPost = postRepository.save(post);
        return convertToDTO(updatedPost);
    }

    @Override
    public void deletePost(Long id) throws PostException, IOException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException("Không tìm thấy bài viết để xóa"));

        if (post.getThumbnail() != null) {
            imageStorageService.deleteBlogImage(post.getThumbnail());
        }
        postRepository.delete(post);
    }

    @Override
    public PostDTO getPostById(Long id) throws PostException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException("Bài viết không tồn tại"));
        return convertToDTO(post);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getLatestPosts(int limit) {
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .limit(limit)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PostDTO convertToDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getThumbnail(),
                post.getShortDescription(),
                post.getUser().getName(),
                post.getUser().getId(),
                post.getCreatedAt()
        );
    }
}

//  Thay đổi nội dung để cập nhật git history