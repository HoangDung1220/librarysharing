package library_share_app.service;

import java.util.List;

import library_share_app.dto.DocumentUserDTO;

public interface IDocumentUserService {
	public DocumentUserDTO save(DocumentUserDTO document_user);
	public List<DocumentUserDTO> findByUser(Long id);



}
