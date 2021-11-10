package library_share_app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import library_share_app.convert.DocumentUserConvert;
import library_share_app.convert.UserConvert;
import library_share_app.dto.DocumentUserDTO;
import library_share_app.dto.UserDTO;
import library_share_app.entity.DocumentUserEntity;
import library_share_app.repository.DocumentUserRepository;
import library_share_app.service.IDocumentUserService;

@Service
public class DocumentUserService implements IDocumentUserService{
	
	@Autowired
	private DocumentUserRepository repository;
	
	@Autowired
	private DocumentUserConvert convert;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserConvert userConvert;

	@Override
	public DocumentUserDTO save(DocumentUserDTO document_user) {
		DocumentUserEntity entity = repository.save(convert.toEntity(document_user));
		return convert.toDTO(entity);
	}


	@Override
	public List<DocumentUserDTO> findByUser(Long id) {
		
		UserDTO user = userService.findOne(id);
		List<DocumentUserDTO> list = new ArrayList<DocumentUserDTO>();
		if (user!=null) {
			List<DocumentUserEntity> entities = repository.findByUserOrderByIdDesc(userConvert.toEntity(user));
			for (DocumentUserEntity entity:entities) {
				list.add(convert.toDTO(entity));
			}
		}
		return list.isEmpty()?null:list;
	}


	

}
