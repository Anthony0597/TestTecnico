package com.reto.programacion.service;

import com.reto.programacion.dto.PostDTO;
import com.reto.programacion.model.Post;
import com.reto.programacion.security.UsuarioDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public interface IPostService {
    /*public void createPost(Post post);
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

    public PostDTO updatePost(Integer postId, PostDTO postRequest, UserDetails userDetails);*/

    //-----------------Nuevo Service----------------

    public Post createPost(Post post, String username);
    public List<PostDTO> getPosts(String userEmail, Collection<? extends GrantedAuthority> roles);
    public Post getPostsById(Integer id, String userEmail, Collection<? extends GrantedAuthority> roles);
    public PostDTO updatePost(Integer postId, Post post, String userEmail, Collection<? extends GrantedAuthority> roles);
    public void deletePost(Integer postId, String userEmail, Collection<? extends GrantedAuthority> roles);
    public List<PostDTO> getPostsPublic();
}
