package library_share_app.convert;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import library_share_app.dto.DocumentDTO;
import library_share_app.entity.DocumentEntity;

@Component
public class DocumentConvert {

	@Autowired
	private ModelMapper modelMapper;
	
	public DocumentEntity toEntity(DocumentDTO dto) {
		DocumentEntity entity = new DocumentEntity();
		entity = modelMapper.map(dto, DocumentEntity.class);
		return entity;
	}
	
	public DocumentDTO toDTO(DocumentEntity entity) {
		DocumentDTO dto = new DocumentDTO();
		dto = modelMapper.map(entity, DocumentDTO.class);
		dto.setId_Category(entity.getCategory().getId());
	//	dto.setId_user(entity.getUser().getId());
		return dto;
	}
	
}
