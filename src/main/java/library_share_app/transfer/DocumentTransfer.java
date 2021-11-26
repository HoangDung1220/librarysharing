package library_share_app.transfer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import library_share_app.constant.SystemConstant;
import library_share_app.dto.DocumentDTO;
import library_share_app.dto.UserDTO;
import library_share_app.service.impl.CategoryService;
import library_share_app.service.impl.UserService;

@Component
public class DocumentTransfer {
	
	@Autowired
	private CategoryService categoryService ;
	
	@Autowired
	private UserService userService;
	
	
	
	
	public List<DocumentDTO> readDocumentClient(boolean status_personal) {
		Socket soc = null;
		 for (Map.Entry<UserDTO, Socket> item : SystemConstant.list_socket_client.entrySet()) {
			 if (item.getKey().getId()==SystemConstant.id_user_current) {
				soc = item.getValue(); 
			 }
	       } 
	
		List<DocumentDTO> list = new ArrayList<DocumentDTO>();
		try {
			DataInputStream din = new DataInputStream(soc.getInputStream());
			int list_size = din.readInt();
			for (int i=1;i<=list_size;i++)
			{	
				DocumentDTO dto = new DocumentDTO();
				dto.setDisplayFileName(din.readUTF());
				dto.setDescription(din.readUTF());
				dto.setFileName(din.readUTF());
				String[] d = din.readUTF().split(" ");
				if (d.length>0) {
				dto.setTime(d[0]);
				} else dto.setTime("");
				dto.setSizeFile(din.readUTF());
				dto.setStatus(din.readBoolean());
				dto.setId_Category(din.readLong());
				dto.setId_user(din.readLong());
				dto.setId(din.readLong());
				dto.setCategory(categoryService.findOneById(dto.getId_Category()));
				dto.setUser(userService.findOne(dto.getId_user()));
				if (status_personal) {
					dto.setStatusImportanceWithUser(din.readBoolean());
				}
				list.add(dto);	
			}
		
	} catch (IOException e) {
		e.printStackTrace();
	}
		return list;
	}
	
	public List<DocumentDTO> readDocumentClient1(Socket soc,boolean status_personal) {
		
		List<DocumentDTO> list = new ArrayList<DocumentDTO>();
		try {
			DataInputStream din = new DataInputStream(soc.getInputStream());
			int list_size = din.readInt();
			for (int i=1;i<=list_size;i++)
			{	
				DocumentDTO dto = new DocumentDTO();
				dto.setDisplayFileName(din.readUTF());
				dto.setDescription(din.readUTF());
				dto.setFileName(din.readUTF());
				String[] d = din.readUTF().split(" ");
				if (d.length>0) {
				dto.setTime(d[0]);
				} else dto.setTime("");
				dto.setSizeFile(din.readUTF());
				dto.setStatus(din.readBoolean());
				dto.setId_Category(din.readLong());
				dto.setId_user(din.readLong());
				dto.setId(din.readLong());
				dto.setCategory(categoryService.findOneById(dto.getId_Category()));
				dto.setUser(userService.findOne(dto.getId_user()));
				if (status_personal) {
					dto.setStatusImportanceWithUser(din.readBoolean());
				}
				list.add(dto);	
			}
		
	} catch (IOException e) {
		e.printStackTrace();
	}
		return list;
	}
	
	public Socket getSocketClient(Long id_client) {
		Socket soc = null;
		 for (Map.Entry<UserDTO, Socket> item : SystemConstant.list_socket_client.entrySet()) {
			 if (item.getKey().getId()==id_client) {
				soc = item.getValue(); 
			 }
	       } 
		 return soc;
	}
	
	public Socket getSocketServer(Long id_client) {
		Socket soc = null;
		 for (Map.Entry<UserDTO, Socket> item : SystemConstant.list_user_active.entrySet()) {
			 if (item.getKey().getId()==SystemConstant.id_user_current) {
				soc = item.getValue(); 
			 }
	       } 
		 return soc;
	}

	
	
}
