package com.reto.programacion.service.impl;

import com.reto.programacion.dto.PostDTO;
import com.reto.programacion.model.Post;
import com.reto.programacion.model.Usuario;
import com.reto.programacion.repository.IPostRepository;
import com.reto.programacion.repository.IUsuarioRepository;
import com.reto.programacion.security.UsuarioDetailsService;
import com.reto.programacion.service.IPostService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    IPostRepository postRepository;
    @Autowired
    IUsuarioRepository usuarioRepository;


    private PostDTO convertToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitulo(post.getTitulo());
        return postDTO;
    }



    @Override
    public Post createPost(Post post, String username) {
        Usuario user = usuarioRepository.findByEmail(username).get();
        post.setUsuario(user);
        post.setFechaPublicacion(LocalDateTime.now());
        postRepository.save(post);
        return post;
    }

    @Override
    public List<PostDTO> getPosts(String userEmail, Collection<? extends GrantedAuthority> roles) {
        List<Post> posts;

        // Si el usuario es ADMIN, ve todos los posts, si no, ve solo los suyos
        if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            System.out.println("Entra como admin");
            posts = postRepository.findAll();

        } else {
            System.out.println("Entra como user");
            posts = postRepository.findByUsuario_Id(usuarioRepository.findByEmail(userEmail).get().getId());
        }

        return posts.stream().map(this::convertToPostDTO).collect(Collectors.toList());
    }

    @Override
    public Post getPostsById(Integer id, String userEmail, Collection<? extends GrantedAuthority> roles) {
        Post postById = postRepository.findById(id).get();
        if(postById!=null){
            System.out.println("Hace la consulta");
            // Si el usuario no es el dueño del post y tampoco es ADMIN, se lanza un error
            if (!postById.getUsuario().getEmail().equals(userEmail) && !roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                throw new AccessDeniedException("No tienes permiso para ver este post");
            }else {
                System.out.println("Es admin ni le pertenece");
                return postById;
            }

        }else {
            return null;
        }

    }

    @Override
    public PostDTO updatePost(Integer postId, Post post, String userEmail, Collection<? extends GrantedAuthority> roles) {
        System.out.println("Data Post: "+post.getId()+post.getTitulo()+post.getContenido()+ post.getUsuario());

        Post postById = postRepository.findById(postId).get();
        if(postById!=null){
            // Si el usuario no es el dueño del post y tampoco es ADMIN, se lanza un error
            if (!postById.getUsuario().getEmail().equals(userEmail) && !roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                throw new AccessDeniedException("No tienes permiso para modificar este post");
            }

            // Se actualizan los datos
            post.setId(postById.getId());
            post.setFechaPublicacion(postById.getFechaPublicacion());
            post.setFechaModificacion(LocalDateTime.now());
            post.setUsuario(postById.getUsuario());

            postRepository.save(post);

            return convertToPostDTO(post);
        }else {
            return null;
        }


    }

    @Override
    public void deletePost(Integer postId, String userEmail, Collection<? extends GrantedAuthority> roles) {
        Post post = postRepository.findById(postId).get();

        // Verifica permisos
        if (!post.getUsuario().getEmail().equals(userEmail) && !roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("No tienes permiso para eliminar este post");
        }

        postRepository.delete(post);
    }

    @Override
    public List<PostDTO> getPostsPublic() {
        List<Post> posts;
        posts = postRepository.findByStatus(true);
        return posts.stream().map(this::convertToPostDTO).collect(Collectors.toList());
    }
}
