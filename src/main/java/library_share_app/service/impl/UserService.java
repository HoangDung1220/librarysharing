package library_share_app.service.impl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
	
	public void getUserActive() {
		Socket soc=null;
		for (Map.Entry<UserDTO, Socket> item : SystemConstant.list_user_active.entrySet()) {
			 if (item.getKey().getId()==SystemConstant.id_user_current) {
				soc = item.getValue(); 
			 }
	       } 
		try {
			DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
			dos.writeInt(SystemConstant.list_user_active.size());
			List<UserDTO> users = new ArrayList<UserDTO>();
			 Set<UserDTO> keySet = SystemConstant.list_user_active.keySet();
		        for (UserDTO key : keySet) {
		        	users.add(key);
		        }			
		    for (UserDTO dto :users) {
			dos.writeLong(dto.getId());
			dos.writeUTF(dto.getFullname());
			dos.writeUTF(dto.getGmail());
			dos.writeUTF(dto.getNameRoom());
			dos.writeUTF(dto.getUsername());
			dos.writeUTF(dto.getPassword());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	@Override
	public UserDTO findOne(Long id) {
		Optional<UserEntity> entity = userRepository.findById(id);
		UserDTO dto = userConvert.toDTO(entity.get());
		return dto;
	}

	@Override
	public UserDTO findByGmail(String gmail) {
		Optional<UserEntity> entity = userRepository.findOneByGmail(gmail);
		UserDTO dto = null;
		if (entity.isPresent()) {
			dto = userConvert.toDTO(entity.get());
		}
		return dto;
	}

}
