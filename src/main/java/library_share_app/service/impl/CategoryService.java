package library_share_app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import library_share_app.convert.CategoryConvert;
import library_share_app.dto.CategoryDTO;
import library_share_app.entity.CategoryEntity;
import library_share_app.repository.CategoryRepository;
import library_share_app.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private CategoryConvert categoryConvert;
	

	@Override
	public List<CategoryDTO> findAll() {
		List<CategoryDTO> listDto = new ArrayList<CategoryDTO>();
		List<CategoryEntity> list = categoryRepo.findAll();
		for (CategoryEntity item:list) {
			CategoryDTO dto = categoryConvert.toDTO(item);
			listDto.add(dto);
		}
		return listDto;
	}


	@Override
	public CategoryDTO findOneById(long id) {
		Optional<CategoryEntity> category = categoryRepo.findById(id);
		CategoryDTO dto = categoryConvert.toDTO(category.get());
		return dto;
	}

}
