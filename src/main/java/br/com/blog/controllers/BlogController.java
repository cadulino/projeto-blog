package br.com.blog.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.blog.dao.BlogDao;
import br.com.blog.models.Blog;

@Controller
@RequestMapping("/blogs")
public class BlogController {

	@Autowired
	private BlogDao dao;
	
	@GetMapping
    public ModelAndView index(ModelMap model) {
		List<Blog> blogs = dao.findAll();
		model.addAttribute("blogs", blogs);
        return new ModelAndView("index", model);
    }
	
	@GetMapping("/cadastro")
    public ModelAndView preSalvar(@ModelAttribute Blog blog){
        return new ModelAndView("blog/cadastro","blog", blog);
    }
	
	@PostMapping("/salvar")
    public ModelAndView salvar(@ModelAttribute("blog") Blog blog, RedirectAttributes attr) {
		blog.setDateTime(LocalDateTime.now());
        dao.save(blog);
        attr.addFlashAttribute("mensagem", "Blog cadastrado com sucesso.");
        return new ModelAndView("redirect:/blogs");
    }
	
}
