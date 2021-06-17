package br.com.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.blog.models.Comentario;

public interface ComentarioDao extends JpaRepository<Comentario, Long>{

}
