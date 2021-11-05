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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import library_share_app.constant.SystemConstant;
import library_share_app.convert.DocumentConvert;
import library_share_app.dto.DocumentDTO;
import library_share_app.entity.DocumentEntity;
import library_share_app.repository.DocumentRepository;
import library_share_app.service.IDocumentService;

@Service
public class DocumentService implements IDocumentService{

	@Autowired
	private DocumentRepository repository;
	
	@Autowired
	private DocumentConvert convert;
	
	@Autowired
	private CategoryService category;
	
	private DataInputStream din;
	
	private DataOutputStream dos;
	
	@Override
	public synchronized DocumentDTO save() {
		
		
		DocumentDTO document = new DocumentDTO();

		 try {
				 din = new DataInputStream(SystemConstant.socket.getInputStream());
				 dos = new DataOutputStream(SystemConstant.socket.getOutputStream());

				String display_name = din.readUTF();
				String description = din.readUTF();
				String file_name = din.readUTF();
				boolean status = din.readBoolean();
				long id_category = din.readLong();

				document = new DocumentDTO();
				document.setDisplayFileName(display_name);
				document.setDescription(description);
				document.setStatus(status);
				document.setFileName(file_name);
				document.setCategory(category.findOneById(id_category));
				long fileSize = din.readLong();
				fileReceive(fileSize,file_name);
				document.setSizeFile(String.valueOf(fileSize)+" bytes");
				document.setSharedDate(new Timestamp(System.currentTimeMillis()));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		DocumentEntity entity = convert.toEntity(document);
		DocumentEntity output = repository.save(entity);
		return convert.toDTO(output);
	}
	
	
	@Override
	public synchronized  void findAll() {
		
		List<DocumentDTO> list = new ArrayList<DocumentDTO>();
		List<DocumentEntity> entities = repository.findAll();
		for (DocumentEntity entity:entities) {
			DocumentDTO dto = convert.toDTO(entity);
			list.add(dto);
		}
		for (Socket soc:SystemConstant.sockets) {
		try {
			DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
			dos.writeInt(list.size());
			for (DocumentDTO dto :list) {
				dos.writeUTF(dto.getDisplayFileName());
				dos.writeUTF(dto.getDescription());
				dos.writeUTF(dto.getFileName());
			//	dos.writeUTF(String.valueOf(dto.getSharedDate()));
				dos.writeUTF(dto.getSizeFile());
				dos.writeBoolean(dto.isStatus());
				dos.writeLong(dto.getId_Category());
				dos.writeLong(dto.getId());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void save1() {
			 try {
					DataInputStream din = new DataInputStream(SystemConstant.socket.getInputStream());
					String kq = din.readUTF();
					System.out.println(kq);
					kq = din.readUTF();
					System.out.println(kq);
					kq = din.readUTF();
					System.out.println(kq);
					boolean kq1 = din.readBoolean();
					System.out.println(kq1);
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
			dos.writeInt(0);
			dos.flush();
			for (int i = 0; i < fileSize; i++) {
				bos.write(din.read());
				bos.flush();
			}
			bos.close();
		} catch (IOException e) {
		}
	}

	
	
	
}
