package br.com.blog.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.blog.dao.BlogDao;
import br.com.blog.dao.ComentarioDao;
import br.com.blog.dao.NoticiaDao;
import br.com.blog.models.Noticia;
import br.com.blog.models.Blog;
import br.com.blog.models.Comentario;

@Controller
@RequestMapping("/blogs/{idBlog}")
public class NoticiaController {
	
	@Autowired
	private NoticiaDao dao;
	
	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private ComentarioDao comentarioDao;
	
	@GetMapping("/noticias")
    public ModelAndView index(@PathVariable Long idBlog, ModelMap model) {
		List<Noticia> noticias = dao.getNoticiasByBlog(idBlog);
		Blog blog = blogDao.getOne(idBlog);
		model.addAttribute("noticias", noticias);
		model.addAttribute("blogSelecionado", blog);
        return new ModelAndView("noticia/index", model);
    }
	
	@GetMapping("/noticias/cadastro")
    public ModelAndView preSalvar(@PathVariable("idBlog") Long idBlog,@ModelAttribute("noticia") Noticia noticia, ModelMap model){
		model.addAttribute("blogSelecionado", idBlog);
        return new ModelAndView("noticia/cadastro");
    }
	
	@PostMapping("noticias/salvar")
    public ModelAndView salvar(@PathVariable("idBlog") Long idBlog, @ModelAttribute("noticia") Noticia noticia, RedirectAttributes attr) {
		noticia.setDateTime(LocalDateTime.now());
		noticia.setBlog(blogDao.findOne(idBlog));
        dao.save(noticia);
        attr.addFlashAttribute("mensagem", "Notícia cadastrado com sucesso.");
        return new ModelAndView("redirect:/blogs/" + idBlog + "/noticias");
    }
	
	@GetMapping("/noticias/{idNoticia}")
	public ModelAndView getNoticia(@PathVariable Long idNoticia,@PathVariable Long idBlog, ModelMap model, @ModelAttribute("comentario") Comentario comentario) {
		Noticia noticia = dao.getOne(idNoticia);
		model.addAttribute("noticia", noticia);
		model.addAttribute("idBlog", idBlog);
		return new ModelAndView("/noticia/noticia-detail");
	}
	
	@PostMapping("noticias/{idNoticia}/salvar/comentarios")
    public ModelAndView salvarComentario(@PathVariable("idBlog") Long idBlog, @PathVariable("idNoticia") Long idNoticia, @ModelAttribute("comentario") Comentario comentario, RedirectAttributes attr) {
		comentario.setDateTime(LocalDateTime.now());
		comentario.setAutor("Anônimo");
		comentario.setNoticia(dao.getOne(idNoticia));
        comentarioDao.save(comentario);
        attr.addFlashAttribute("mensagem", "Comentário cadastrado com sucesso.");
        return new ModelAndView("redirect:/blogs/" + idBlog + "/noticias/" + idNoticia);
    }
	
	@GetMapping("noticias/{idNoticia}/remover")
    public ModelAndView removerNoticia(@PathVariable("idBlog") Long idBlog, @PathVariable("idNoticia") Long idNoticia, ModelMap model) {
		model.addAttribute("idBlog", idBlog);
		model.addAttribute("idNoticia", idNoticia);
		dao.delete(idNoticia);
        return new ModelAndView("redirect:/blogs/" + idBlog + "/noticias");
    }
	
	@GetMapping("noticias/{idNoticia}/comentarios/{idComentario}/remover")
    public ModelAndView removerComentario(@PathVariable("idBlog") Long idBlog, @PathVariable("idNoticia") Long idNoticia, @PathVariable("idComentario") Long idComentario, ModelMap model) {
		model.addAttribute("idBlog", idBlog);
		model.addAttribute("idNoticia", idNoticia);
		model.addAttribute("idComentario", idComentario);
        comentarioDao.delete(idComentario);
        return new ModelAndView("redirect:/blogs/" + idBlog + "/noticias/" + idNoticia);
    }
	
}
