package br.com.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.blog.models.Noticia;

public interface NoticiaDao extends JpaRepository<Noticia, Long> {
	@Query("select n from Noticia n where n.blog.id=?1")
    public List<Noticia> getNoticiasByBlog(Long idBlog);
}
