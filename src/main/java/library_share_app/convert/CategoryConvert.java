package library_share_app.convert;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import library_share_app.dto.CategoryDTO;
import library_share_app.entity.CategoryEntity;

@Component
public class CategoryConvert {
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public CategoryDTO toDTO(CategoryEntity entity) {
		CategoryDTO dto = modelMapper.map(entity, CategoryDTO.class);
		return dto;
	}
	
	public CategoryEntity toEntity(CategoryDTO dto) {
		CategoryEntity entity = modelMapper.map(dto, CategoryEntity.class);
		return entity;
	}
	
	
	

}
