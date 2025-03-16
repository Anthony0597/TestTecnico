package com.reto.programacion.service.impl;

import com.reto.programacion.dto.PostDTO;
import com.reto.programacion.model.Post;
import com.reto.programacion.repository.IPostRepository;
import com.reto.programacion.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    IPostRepository postRepository;

    @Override
    public void createPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void updatePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post findPostById(Integer id) {
        return postRepository.findById(id).get();
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<PostDTO> findAllPostDTO() {
        return postRepository.findAll().stream().map(this::convertToPostDTO).toList();
    }

    @Override
    public List<Post> findPostsByUser(Integer userId) {
        return postRepository.findByUsuario_Id(userId);
    }

    @Override
    public List<Post> findPostsByState(Boolean state) {
        return postRepository.findByPrivado(state);
    }

    @Override
    public PostDTO findPostDTOById(Integer id) {
        return convertToPostDTO(postRepository.findById(id).get());
    }

    @Override
    public List<PostDTO> findAllPostsDTO() {
        return postRepository.findAll().stream().map(this::convertToPostDTO).toList();
    }

    @Override
    public List<PostDTO> findPostsDTOByUser(Integer id) {
        return postRepository.findByUsuario_Id(id).stream().map(this::convertToPostDTO).toList();
    }

    @Override
    public List<PostDTO> findPostsDTOByState(Boolean state) {
        return postRepository.findByPrivado(state).stream().map(this::convertToPostDTO).toList();
    }

    private PostDTO convertToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitulo(post.getTitulo());
        return postDTO;
    }
}
