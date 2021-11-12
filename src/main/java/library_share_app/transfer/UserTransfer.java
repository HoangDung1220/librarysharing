package library_share_app.transfer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import library_share_app.constant.SystemConstant;
import library_share_app.dto.UserDTO;

@Component
public class UserTransfer {
	
	public List<UserDTO> readUserClient(){
		Socket soc = null;
		 for (Map.Entry<UserDTO, Socket> item : SystemConstant.list_socket_client.entrySet()) {
			 if (item.getKey().getId()==SystemConstant.id_user_current) {
				soc = item.getValue(); 
			 }
	       } 
	
		List<UserDTO> users = new ArrayList<UserDTO>();
		try {
			DataInputStream din = new DataInputStream(soc.getInputStream());
			int list_size = din.readInt();
			for (int i=1;i<=list_size;i++)
			{	
				
				UserDTO dto = new UserDTO();
				dto.setId(din.readLong());
				dto.setFullname(din.readUTF());
				dto.setGmail(din.readUTF());
				dto.setNameRoom(din.readUTF());
				dto.setUsername(din.readUTF());
				dto.setPassword(din.readUTF());
				
				users.add(dto);
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return users;
		
	}

}
