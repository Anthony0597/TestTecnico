package com.reto.programacion.service;

import com.reto.programacion.controller.PostController;
import com.reto.programacion.dto.PostDTO;
import com.reto.programacion.model.Post;
import com.reto.programacion.service.IPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Mock
    private IPostService postService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePost() {
        // Arrange
        Post post = new Post();
        post.setTitulo("Test Post");
        when(authentication.getName()).thenReturn("testuser");
        when(postService.createPost(any(Post.class), anyString())).thenReturn(post);

        // Act
        ResponseEntity<Post> response = postController.createPost(post, authentication);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(post, response.getBody());
        verify(postService, times(1)).createPost(any(Post.class), anyString());
    }

    @Test
    void testGetPosts() {
        // Arrange
        PostDTO postDTO = new PostDTO();
        postDTO.setTitulo("Test Post");
        List<PostDTO> posts = Arrays.asList(postDTO);
        when(authentication.getName()).thenReturn("testuser");
        when(authentication.getAuthorities()).thenReturn((Collection) Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
        when(postService.getPosts(anyString(), anyCollection())).thenReturn(posts);

        // Act
        ResponseEntity<List<PostDTO>> response = postController.getPosts(authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(posts, response.getBody());
        verify(postService, times(1)).getPosts(anyString(), anyCollection());
    }

    @Test
    void testGetPostsById() {
        // Arrange
        Post post = new Post();
        post.setTitulo("Test Post");
        when(authentication.getName()).thenReturn("testuser");
        when(authentication.getAuthorities()).thenReturn((Collection) Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
        when(postService.getPostsById(anyInt(), anyString(), anyCollection())).thenReturn(post);

        // Act
        ResponseEntity<Post> response = postController.getPostsById(authentication, 1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(post, response.getBody());
        verify(postService, times(1)).getPostsById(anyInt(), anyString(), anyCollection());
    }

    @Test
    void testUpdatePost() {
        // Arrange
        PostDTO postDTO = new PostDTO();
        postDTO.setTitulo("Updated Post");
        Post post = new Post();
        post.setTitulo("Test Post");
        when(authentication.getName()).thenReturn("testuser");
        when(authentication.getAuthorities()).thenReturn((Collection) Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
        when(postService.updatePost(anyInt(), any(Post.class), anyString(), anyCollection())).thenReturn(postDTO);

        // Act
        ResponseEntity<PostDTO> response = postController.updatePost(1, post, authentication);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(postDTO, response.getBody());
        verify(postService, times(1)).updatePost(anyInt(), any(Post.class), anyString(), anyCollection());
    }

    @Test
    void testDeletePost() {
        // Arrange
        when(authentication.getName()).thenReturn("testuser");
        when(authentication.getAuthorities()).thenReturn((Collection) Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
        doNothing().when(postService).deletePost(anyInt(), anyString(), anyCollection());

        // Act
        ResponseEntity<Void> response = postController.deletePost(1, authentication);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(postService, times(1)).deletePost(anyInt(), anyString(), anyCollection());
    }

    @Test
    void testGetAllPublicPosts() {
        // Arrange
        PostDTO postDTO = new PostDTO();
        postDTO.setTitulo("Public Post");
        List<PostDTO> posts = Arrays.asList(postDTO);
        when(postService.getPostsPublic()).thenReturn(posts);

        // Act
        ResponseEntity<List<PostDTO>> response = postController.getAllPublicPosts();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(posts, response.getBody());
        verify(postService, times(1)).getPostsPublic();
    }
}