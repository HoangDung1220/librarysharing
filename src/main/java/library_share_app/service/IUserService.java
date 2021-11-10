package library_share_app.service;

import library_share_app.dto.UserDTO;

public interface IUserService {
	
	public void findOneByUsernameAndPassword(); 
	
	public UserDTO findOne(Long id);
	
	public UserDTO findByGmail(String gmail);
	

}
