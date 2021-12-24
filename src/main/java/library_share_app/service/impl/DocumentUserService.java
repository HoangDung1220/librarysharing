package library_share_app.service.impl;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import library_share_app.constant.SystemConstant;
import library_share_app.convert.DocumentConvert;
import library_share_app.convert.DocumentUserConvert;
import library_share_app.convert.UserConvert;
import library_share_app.dto.DocumentDTO;
import library_share_app.dto.DocumentUserDTO;
import library_share_app.dto.UserDTO;
import library_share_app.entity.DocumentEntity;
import library_share_app.entity.DocumentUserEntity;
import library_share_app.entity.UserEntity;
import library_share_app.repository.DocumentUserRepository;
import library_share_app.service.IDocumentUserService;
import library_share_app.transfer.DocumentTransfer;

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
	
	@Autowired 
	private DocumentService documentService;
	
	@Autowired
	private DocumentConvert documentConvert;
	
	@Autowired
	private DocumentTransfer documentTranfer;

	@Override
	public DocumentUserDTO save(DocumentUserDTO document_user) {
		document_user.setStatusDelete(true);
		document_user.setStatusFavourite(false);
		DocumentUserEntity entity = repository.save(convert.toEntity(document_user));
		return convert.toDTO(entity);
	}


	@Override
	public List<DocumentUserDTO> findByUser(Long id,boolean check) {
		
		UserDTO user = userService.findOne(id);
		List<DocumentUserDTO> list = new ArrayList<DocumentUserDTO>();
		if (user!=null) {
			List<DocumentUserEntity> entities = repository.findByUserAndStatusDeleteOrderByIdDesc(userConvert.toEntity(user),check);
			for (DocumentUserEntity entity:entities) {
				list.add(convert.toDTO(entity));
			}
		}
		return list.isEmpty()?null:list;
	}


	@Override
	public void deleteDocumentUser() {
		Socket socket = documentTranfer.getSocketServer(SystemConstant.id_user_current);
		try {
			DataInputStream din = new DataInputStream(socket.getInputStream());
			Long id_document = din.readLong();
			UserEntity user = userConvert.toEntity(userService.findOne(SystemConstant.id_user_current));
			DocumentEntity document = documentConvert.toEntity(documentService.findOne(id_document));
			if (user!=null && document!=null) {
				List<DocumentUserEntity> list = repository.findByUserAndDocument(user, document);
				for (DocumentUserEntity entity:list) {
					DocumentUserEntity en = entity;
					en.setDateDelete(new Timestamp(System.currentTimeMillis()));
					en.setStatusDelete(false);
					repository.save(en);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void restoreDocumentUser() {
		Socket socket = documentTranfer.getSocketServer(SystemConstant.id_user_current);
		try {
			DataInputStream din = new DataInputStream(socket.getInputStream());
			Long id_document = din.readLong();
			UserEntity user = userConvert.toEntity(userService.findOne(SystemConstant.id_user_current));
			DocumentEntity document = documentConvert.toEntity(documentService.findOne(id_document));
			if (user!=null && document!=null) {
				List<DocumentUserEntity> list = repository.findByUserAndDocument(user, document);
				for (DocumentUserEntity entity:list) {
					DocumentUserEntity en = entity;
					en.setDateDelete(new Timestamp(System.currentTimeMillis()));
					en.setStatusDelete(true);
					repository.save(en);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void importanceDocumentUser() {
		Socket socket = documentTranfer.getSocketServer(SystemConstant.id_user_current);
		try {
			DataInputStream din = new DataInputStream(socket.getInputStream());
			Long id_document = din.readLong();
			UserEntity user = userConvert.toEntity(userService.findOne(SystemConstant.id_user_current));
			DocumentEntity document = documentConvert.toEntity(documentService.findOne(id_document));
			if (user!=null && document!=null) {
				List<DocumentUserEntity> list = repository.findByUserAndDocument(user, document);
				for (DocumentUserEntity entity:list) {
					DocumentUserEntity en = entity;
					en.setStatusFavourite(true);
					repository.save(en);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public List<DocumentUserDTO> findByUserFavourite(Long id, boolean check, boolean check_favourite) {
		UserDTO user = userService.findOne(id);
		List<DocumentUserDTO> list = new ArrayList<DocumentUserDTO>();
		if (user!=null) {
			List<DocumentUserEntity> entities 
			= repository.findByUserAndStatusDeleteAndStatusFavouriteOrderByIdDesc(userConvert.toEntity(user), check, check_favourite);
			for (DocumentUserEntity entity:entities) {
				list.add(convert.toDTO(entity));
			}
		}
		return list.isEmpty()?null:list;
	}


	@Override
	public void unremarkDocument() {
		Socket socket = documentTranfer.getSocketServer(SystemConstant.id_user_current);
		try {
			DataInputStream din = new DataInputStream(socket.getInputStream());
			Long id_document = din.readLong();
			UserEntity user = userConvert.toEntity(userService.findOne(SystemConstant.id_user_current));
			DocumentEntity document = documentConvert.toEntity(documentService.findOne(id_document));
			if (user!=null && document!=null) {
				List<DocumentUserEntity> list = repository.findByUserAndDocument(user, document);
				for (DocumentUserEntity entity:list) {
					DocumentUserEntity en = entity;
					en.setStatusFavourite(false);
					repository.save(en);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public void shareDocument() {
		Socket socket = documentTranfer.getSocketServer(SystemConstant.id_user_current);
		try {
			DataInputStream din = new DataInputStream(socket.getInputStream());
			Long id_user = din.readLong();
			Long id_document = din.readLong();
			String email  = din.readUTF();
			System.out.println(id_user+" "+id_document+" "+email);
			DocumentDTO document = documentService.findOne(id_document);
			String emails[] = email.split(";");
			for (String st : emails) {
				UserDTO user = userService.findByGmail(st);
				if (user != null) {
					DocumentUserEntity entity= new DocumentUserEntity();
					entity.setDocument(documentConvert.toEntity(document));
					entity.setStatus(true);
					entity.setUser(userConvert.toEntity(user));
					entity.setId_userShare(id_user);
					entity.setStatusDelete(true);
					entity.setDateShare(new Timestamp(System.currentTimeMillis()));
					repository.save(entity);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void delete() {
		Date date = new Date();
		date.setMonth(date.getMonth()-1);
		List<DocumentUserDTO> listdto = new ArrayList<DocumentUserDTO>();
		List<DocumentUserEntity> list = repository.findByDateDelete(date);
		for (DocumentUserEntity en:list) {
			repository.delete(en);
		}
		
		
		
	}




	

}
