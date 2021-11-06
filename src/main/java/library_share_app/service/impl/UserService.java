package library_share_app.service.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import library_share_app.constant.SystemConstant;
import library_share_app.convert.UserConvert;
import library_share_app.dto.UserDTO;
import library_share_app.entity.UserEntity;
import library_share_app.repository.UserRepository;
import library_share_app.service.IUserService;

@Service
public class UserService implements IUserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserConvert userConvert;

	@Override
	public void findOneByUsernameAndPassword() {
		DataInputStream din;
		try {
			din = new DataInputStream(SystemConstant.socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(SystemConstant.socket.getOutputStream());
			String username = din.readUTF();
			String password = din.readUTF();
			UserEntity user = userRepository.findOneByUsernameAndPassword(username, password).get();
			UserDTO dto = userConvert.toDTO(user); 
			
			dos.writeLong(dto.getId());
			dos.writeUTF(dto.getFullname());
			dos.writeUTF(dto.getGmail());
			dos.writeUTF(dto.getNameRoom());
			dos.writeUTF(dto.getUsername());
			dos.writeUTF(dto.getPassword());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
