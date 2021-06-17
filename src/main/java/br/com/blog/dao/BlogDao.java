package br.com.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.blog.models.Blog;


public interface BlogDao extends JpaRepository<Blog, Long>{

}
