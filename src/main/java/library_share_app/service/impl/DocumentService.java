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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import library_share_app.constant.SystemConstant;
import library_share_app.convert.DocumentConvert;
import library_share_app.convert.DocumentUserConvert;
import library_share_app.convert.UserConvert;
import library_share_app.dto.DocumentDTO;
import library_share_app.dto.UserDTO;
import library_share_app.entity.DocumentEntity;
import library_share_app.entity.DocumentUserEntity;
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
		//DocumentEntity entity = convert.toEntity(document);
		DocumentDTO output = convert.toDTO(repository.save(convert.toEntity(document)));
		if (email_share.length()>0) {
			SaveDocumentUser(email_share, output);
		}
		return output;
	}
	
	
	@Override
	public  void findAll() {
		System.out.println("server find all");
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
		 System.out.println("server: "+soc);
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
	
	
	
}
