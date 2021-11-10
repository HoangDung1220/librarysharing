package library_share_app.service.impl;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import library_share_app.constant.SystemConstant;
import library_share_app.convert.CategoryConvert;
import library_share_app.convert.DocumentConvert;
import library_share_app.convert.DocumentUserConvert;
import library_share_app.convert.UserConvert;
import library_share_app.dto.CategoryDTO;
import library_share_app.dto.DocumentDTO;
import library_share_app.dto.DocumentUserDTO;
import library_share_app.dto.UserDTO;
import library_share_app.entity.DocumentEntity;
import library_share_app.entity.DocumentUserEntity;
import library_share_app.entity.UserEntity;
import library_share_app.repository.DocumentRepository;
import library_share_app.service.IDocumentService;

@Service
public class DocumentService implements IDocumentService{

	@Autowired
	private DocumentRepository repository;
	
	@Autowired
	private DocumentConvert convert;
	
	@Autowired
	private UserConvert userConvert;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DocumentUserService document_userService;
	
	@Autowired
	private DocumentUserConvert document_userConvert;
	
	@Autowired
	private CategoryService category;
	
	@Autowired
	private CategoryConvert categoryConvert;
	
	private DataInputStream din;
	
	private DataOutputStream dos;
	
	@Override
	public  DocumentDTO save() {
		Socket soc = null;
		String email_share = "";
		 for (Map.Entry<UserDTO, Socket> item : SystemConstant.list_user_active.entrySet()) {
			 if (item.getKey().getId()==SystemConstant.id_user_current) {
				soc = item.getValue(); 
			 }
	       } 
		
		DocumentDTO document = new DocumentDTO();

		 try {
			 System.out.println("server add");
				 din = new DataInputStream(soc.getInputStream());
				 dos = new DataOutputStream(soc.getOutputStream());


				String display_name = din.readUTF();
				String description = din.readUTF();
				String file_name = din.readUTF();
				boolean status = din.readBoolean();
				long id_category = din.readLong();
				long id_user = din.readLong();

				document = new DocumentDTO();
				document.setDisplayFileName(display_name);
				document.setDescription(description);
				document.setStatus(status);
				document.setFileName(file_name);
				document.setCategory(category.findOneById(id_category));
				document.setUser(userService.findOne(id_user));
				long fileSize = din.readLong();
				email_share = din.readUTF();
				fileReceive(fileSize,file_name);
				document.setSizeFile(String.valueOf(fileSize)+" bytes");
				document.setSharedDate(new Timestamp(System.currentTimeMillis()));
				System.out.println("Service "+email_share);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		DocumentDTO output = convert.toDTO(repository.save(convert.toEntity(document)));
		SaveDocumentUserSelf(SystemConstant.id_user_current,output);
		if (email_share.length()>0) {
			SaveDocumentUser(email_share, output);
		}
		return output;
	}
	
	
	@Override
	public  void findAll() {
		List<DocumentDTO> list = new ArrayList<DocumentDTO>();
		List<DocumentEntity> entities = repository.findAll();
		for (DocumentEntity entity:entities) {
			DocumentDTO dto = convert.toDTO(entity);
			list.add(dto);
		}
		Socket soc = null;
		 for (Map.Entry<UserDTO, Socket> item : SystemConstant.list_user_active.entrySet()) {
			 if (item.getKey().getId()==SystemConstant.id_user_current) {
				soc = item.getValue(); 
			 }
	       } 
		try {
			dos = new DataOutputStream(soc.getOutputStream());
			dos.writeInt(list.size());

			for (DocumentDTO dto :list) {
				dos.writeUTF(dto.getDisplayFileName());
				dos.writeUTF(dto.getDescription());
				dos.writeUTF(dto.getFileName());
				dos.writeUTF(String.valueOf(dto.getSharedDate()));
				dos.writeUTF(dto.getSizeFile());
				dos.writeBoolean(dto.isStatus());
				dos.writeLong(dto.getId_Category());
				dos.writeLong(dto.getId_user());
				dos.writeLong(dto.getId());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		

	
	public void fileReceive(long fileSize,String file_name) throws IOException {
		StringBuilder source = new StringBuilder();
		source.append(SystemConstant.download+file_name);
		File f = new File(source.toString());
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(f));
			
			for (int i = 0; i < fileSize; i++) {
				bos.write(din.read());
				bos.flush();
			}
			bos.close();
		} catch (IOException e) {
		}
	}

	
	public void SaveDocumentUser(String emailShare,DocumentDTO document) {
		String[] emails = emailShare.split(";");
		
		for (String email :emails) {
			UserDTO dto = userService.findByGmail(email);
			if (dto != null) {
				DocumentUserEntity entity= new DocumentUserEntity();
				entity.setDocument(convert.toEntity(document));
				entity.setStatus(true);
				entity.setUser(userConvert.toEntity(dto));
				document_userService.save(document_userConvert.toDTO(entity));
			}
		}
		
	}
	
	public void SaveDocumentUserSelf(Long id,DocumentDTO document) {
		
			UserDTO dto = userService.findOne(id);
			if (dto != null) {
				DocumentUserEntity entity= new DocumentUserEntity();
				entity.setDocument(convert.toEntity(document));
				entity.setStatus(true);
				entity.setUser(userConvert.toEntity(dto));
				document_userService.save(document_userConvert.toDTO(entity));
			}
		
		
	}


	@Override
	public void findAllPersonal() {
		List<DocumentUserDTO> document_user = document_userService.findByUser(SystemConstant.id_user_current);
		List<DocumentDTO> documents = new ArrayList<DocumentDTO>();
		for (DocumentUserDTO doc : document_user) {
			DocumentDTO dto = null;
			Optional<DocumentEntity> entity = repository.findById(doc.getId_document());
			if (entity.isPresent()) {
				dto = convert.toDTO(entity.get());
				documents.add(dto);
			}
		}
		
		Socket soc = null;
		 for (Map.Entry<UserDTO, Socket> item : SystemConstant.list_user_active.entrySet()) {
			 if (item.getKey().getId()==SystemConstant.id_user_current) {
				soc = item.getValue(); 
			 }
	       } 
		try {
			dos = new DataOutputStream(soc.getOutputStream());
			dos.writeInt(documents.size());

			for (DocumentDTO dto :documents) {
				dos.writeUTF(dto.getDisplayFileName());
				dos.writeUTF(dto.getDescription());
				dos.writeUTF(dto.getFileName());
				dos.writeUTF(String.valueOf(dto.getSharedDate()));
				dos.writeUTF(dto.getSizeFile());
				dos.writeBoolean(dto.isStatus());
				dos.writeLong(dto.getId_Category());
				dos.writeLong(dto.getId_user());
				dos.writeLong(dto.getId());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}


	@Override
	public DocumentDTO findOne(Long id) {
		DocumentDTO dto = null;
		Optional<DocumentEntity> entity = repository.findById(id);
		if (entity.isPresent()) {
			dto = convert.toDTO(entity.get());
		}
		return dto;
	}


	@Override
	public List<DocumentDTO> findAllByCategory(Long id_category) {
		List<DocumentDTO> list_dto = new ArrayList<DocumentDTO>();
		CategoryDTO category_dto = category.findOneById(id_category);
		
		if (category_dto!=null) {
			List<DocumentEntity> list = repository.findByCategory(categoryConvert.toEntity(category_dto));
			for (DocumentEntity entity:list) {
				DocumentDTO dto = convert.toDTO(entity);
				list_dto.add(dto);
			}
			
			Socket soc = null;
			 for (Map.Entry<UserDTO, Socket> item : SystemConstant.list_user_active.entrySet()) {
				 if (item.getKey().getId()==SystemConstant.id_user_current) {
					soc = item.getValue(); 
				 }
		       } 
			try {
				dos = new DataOutputStream(soc.getOutputStream());
				dos.writeInt(list.size());

				for (DocumentDTO dto :list_dto) {
					dos.writeUTF(dto.getDisplayFileName());
					dos.writeUTF(dto.getDescription());
					dos.writeUTF(dto.getFileName());
					dos.writeUTF(String.valueOf(dto.getSharedDate()));
					dos.writeUTF(dto.getSizeFile());
					dos.writeBoolean(dto.isStatus());
					dos.writeLong(dto.getId_Category());
					dos.writeLong(dto.getId_user());
					dos.writeLong(dto.getId());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}


	@Override
	public List<DocumentDTO> findAllShared() {
		List<DocumentUserDTO> document_user = document_userService.findByUser(SystemConstant.id_user_current);
		UserDTO user = userService.findOne(SystemConstant.id_user_current);
		List<DocumentDTO> documents = new ArrayList<DocumentDTO>();
		for (DocumentUserDTO doc : document_user) {
			DocumentDTO dto = null;
			DocumentEntity entity = repository.findOneByIdAndUserNot(doc.getId_document(), userConvert.toEntity(user));
			if (entity!=null) {
				dto = convert.toDTO(entity);
				documents.add(dto);
			}
		}
		
		Socket soc = null;
		 for (Map.Entry<UserDTO, Socket> item : SystemConstant.list_user_active.entrySet()) {
			 if (item.getKey().getId()==SystemConstant.id_user_current) {
				soc = item.getValue(); 
			 }
	       } 
		 
			try {
				dos = new DataOutputStream(soc.getOutputStream());
				dos.writeInt(documents.size());

				for (DocumentDTO dto :documents) {
					dos.writeUTF(dto.getDisplayFileName());
					dos.writeUTF(dto.getDescription());
					dos.writeUTF(dto.getFileName());
					dos.writeUTF(String.valueOf(dto.getSharedDate()));
					dos.writeUTF(dto.getSizeFile());
					dos.writeBoolean(dto.isStatus());
					dos.writeLong(dto.getId_Category());
					dos.writeLong(dto.getId_user());
					dos.writeLong(dto.getId());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		 
		 
		 
		return documents;
	}
	
	
	
}
