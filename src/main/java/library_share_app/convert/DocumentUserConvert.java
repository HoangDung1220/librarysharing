package library_share_app.convert;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import library_share_app.dto.DocumentUserDTO;
import library_share_app.entity.DocumentUserEntity;
import library_share_app.service.impl.UserService;

@Component
public class DocumentUserConvert {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserService userService;
	
	public DocumentUserDTO toDTO(DocumentUserEntity entity) {
		DocumentUserDTO dto = modelMapper.map(entity, DocumentUserDTO.class);
		dto.setId_document(entity.getDocument().getId());
		dto.setId_user(entity.getUser().getId());
		if (entity.getId_userShare()!=null) {
		dto.setUserShare(userService.findOne(entity.getId_userShare()));
		}
		return dto;
		
	}
	
	public DocumentUserEntity toEntity(DocumentUserDTO dto) {
		DocumentUserEntity entity = modelMapper.map(dto, DocumentUserEntity.class);
		return entity;
	}
}
