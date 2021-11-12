package library_share_app.service;

import java.util.List;

import library_share_app.dto.DocumentDTO;

public interface IDocumentService {
	
	public DocumentDTO save();
	public void findAll();
	public void findAllPersonal();
	public DocumentDTO findOne(Long id);
	public List<DocumentDTO> findAllByCategory(Long id_category);
	public List<DocumentDTO> findAllShared();
	public List<DocumentDTO> findAllDeletePersonal();
	public List<DocumentDTO> findAllFavouritePersonal();

}
