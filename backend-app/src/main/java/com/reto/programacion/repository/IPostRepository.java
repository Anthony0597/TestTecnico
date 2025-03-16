package com.reto.programacion.repository;

import com.reto.programacion.model.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@Repository
@Transactional
public interface IPostRepository extends JpaRepository<Post, Integer> {
    public List<Post> findByUsuario_Id(Integer userId);
    List<Post> findByPrivado(Boolean privado);
}
