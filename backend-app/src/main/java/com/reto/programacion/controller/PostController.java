package com.reto.programacion.controller;


import com.reto.programacion.dto.PostDTO;
import com.reto.programacion.model.Post;
import com.reto.programacion.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/posts")
public class PostController {
    @Autowired
    private IPostService postService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Post> createPost(@RequestBody Post post, Authentication authentication) {
        String username = authentication.getName();
        Post postResponse = postService.createPost(post, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<PostDTO>> getPosts(Authentication authentication) {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<PostDTO> posts = postService.getPosts(username, authorities);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Post> getPostsById(Authentication authentication, @PathVariable Integer id) {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Post posts = postService.getPostsById(id,username, authorities);
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Integer postId,
                                                      @RequestBody Post post, Authentication authentication) {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        PostDTO updatedPost = postService.updatePost(postId, post, username, authorities);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deletePost(@PathVariable Integer postId,
                                           Authentication authentication) {
        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        postService.deletePost(postId, username, authorities);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/allpublic")
    public ResponseEntity<List<PostDTO>> getAllPublicPosts() {
        List<PostDTO> posts = postService.getPostsPublic();
        return ResponseEntity.ok(posts);
    }
}
