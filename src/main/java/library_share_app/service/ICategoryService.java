package library_share_app.service;

import java.util.List;

import library_share_app.dto.CategoryDTO;

public interface ICategoryService {
	
	public List<CategoryDTO> findAll();
	public CategoryDTO findOneById(long id);

}
