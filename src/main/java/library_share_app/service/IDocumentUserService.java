package library_share_app.service;

import java.util.List;

import library_share_app.dto.DocumentUserDTO;

public interface IDocumentUserService {
	public DocumentUserDTO save(DocumentUserDTO document_user);
	public List<DocumentUserDTO> findByUser(Long id,boolean check);
	public List<DocumentUserDTO> findByUserFavourite(Long id,boolean check,boolean check_favourite);
	public void deleteDocumentUser();
	public void restoreDocumentUser();
	public void importanceDocumentUser();
	public void unremarkDocument();
	public void shareDocument();



}
