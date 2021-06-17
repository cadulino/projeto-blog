package br.com.blog.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Noticia {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime dateTime;
	private String title;
	@Column(columnDefinition = "TEXT")
	private String conteudo;
	
	@ManyToOne()
	@JoinColumn(name="blog_id")
	private Blog blog;
	
	@OneToMany(mappedBy = "noticia")
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	

	public Noticia(Long id, LocalDateTime dateTime, String title, String conteudo, Blog blog) {
		super();
		this.id = id;
		this.dateTime = dateTime;
		this.title = title;
		this.conteudo = conteudo;
		this.blog = blog;
	}


	public Noticia() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	public String getDateTimeFormatted () {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		return this.dateTime.format(formatter);
	}


	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getConteudo() {
		return conteudo;
	}


	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	public String getConteudoMenor() {
		if(conteudo.length() >= 500) {
			return conteudo.substring(0, 500) + "...";
		}
		return conteudo;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}


	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	

	public Blog getBlog() {
		return blog;
	}


	public void setBlog(Blog blog) {
		this.blog = blog;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Noticia other = (Noticia) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}

	

