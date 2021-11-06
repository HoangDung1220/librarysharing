package library_share_app.convert;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import library_share_app.dto.UserDTO;
import library_share_app.entity.UserEntity;

@Component
public class UserConvert {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UserEntity toEntity(UserDTO dto) {
		UserEntity entity = modelMapper.map(dto, UserEntity.class);
		return entity;
	}

	public UserDTO toDTO(UserEntity entity) {
		UserDTO dto = modelMapper.map(entity, UserDTO.class);
		return dto;
	}

}
