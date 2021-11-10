package library_share_app.convert;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import library_share_app.dto.DocumentUserDTO;
import library_share_app.entity.DocumentUserEntity;

@Component
public class DocumentUserConvert {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public DocumentUserDTO toDTO(DocumentUserEntity entity) {
		DocumentUserDTO dto = modelMapper.map(entity, DocumentUserDTO.class);
		dto.setId_document(entity.getDocument().getId());
		dto.setId_user(entity.getUser().getId());
		return dto;
		
	}
	
	public DocumentUserEntity toEntity(DocumentUserDTO dto) {
		DocumentUserEntity entity = modelMapper.map(dto, DocumentUserEntity.class);
		return entity;
	}
}
