package com.reto.programacion.controller;


import com.reto.programacion.dto.PostDTO;
import com.reto.programacion.model.Post;
import com.reto.programacion.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
public class PostController {
    @Autowired
    private IPostService postService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostDTO>>  getPost() {
        var lista = postService.findAllPostsDTO();
        return ResponseEntity.status(200).body(lista);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer id) {
        var post = postService.findPostDTOById(id);
        return ResponseEntity.ok(post);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createPost(@RequestBody Post post) {
        postService.createPost(post);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updatePost(@PathVariable Integer id, @RequestBody Post post) {
        post.setId(id);
        postService.updatePost(post);
    }

    @DeleteMapping(path = "/{id}", consumes = MediaType.ALL_VALUE)
    public void deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
    }
}
