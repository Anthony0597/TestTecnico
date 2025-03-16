package com.reto.programacion.service;

import com.reto.programacion.dto.PostDTO;
import com.reto.programacion.model.Post;

import java.util.List;

public interface IPostService {
    public void createPost(Post post);
    public void updatePost(Post post);
    public void deletePost(Integer id);
    public Post findPostById(Integer id);
    public List<Post> findAllPosts();
    public List<PostDTO> findAllPostDTO();
    public List<Post> findPostsByUser(Integer userId);
    public List<Post> findPostsByState(Boolean state);
    public PostDTO findPostDTOById(Integer id);
    public List<PostDTO> findAllPostsDTO();
    public List<PostDTO> findPostsDTOByUser(Integer id);
    public List<PostDTO> findPostsDTOByState(Boolean state);
}
