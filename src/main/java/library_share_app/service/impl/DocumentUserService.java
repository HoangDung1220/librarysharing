package library_share_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import library_share_app.convert.DocumentUserConvert;
import library_share_app.dto.DocumentUserDTO;
import library_share_app.entity.DocumentUserEntity;
import library_share_app.repository.DocumentUserRepository;
import library_share_app.service.IDocumentUserService;

@Service
public class DocumentUserService implements IDocumentUserService{
	
	@Autowired
	private DocumentUserRepository repository;
	
	@Autowired
	private DocumentUserConvert convert;
	

	@Override
	public DocumentUserDTO save(DocumentUserDTO document_user) {
		DocumentUserEntity entity = repository.save(convert.toEntity(document_user));
		return convert.toDTO(entity);
	}

}
