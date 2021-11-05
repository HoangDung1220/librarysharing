package library_share_app.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import library_share_app.dto.CategoryDTO;
import library_share_app.service.impl.CategoryService;

@Controller
public class BaseController {
	
	@Autowired
	protected CategoryService categoryService;
	
	protected ModelAndView model = new ModelAndView();
	
	@PostConstruct
	protected ModelAndView Init() {
		List<CategoryDTO> list = categoryService.findAll();
		model.addObject("categories", list);
		return model;
	}
}
