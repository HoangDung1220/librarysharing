package library_share_app.service;

import library_share_app.dto.DocumentDTO;

public interface IDocumentService {
	
	public DocumentDTO save();
	public void findAll();
	public void findAllPersonal();
	public DocumentDTO findOne(Long id);
}
