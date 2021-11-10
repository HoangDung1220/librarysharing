package library_share_app.controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import library_share_app.constant.SystemConstant;
import library_share_app.dto.DocumentDTO;
import library_share_app.dto.UserDTO;
import library_share_app.service.impl.DocumentService;
import library_share_app.service.impl.UserService;
import library_share_app.transfer.DocumentTransfer;

@Controller
public class PersonalController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private DocumentTransfer documentTranfer;

	@GetMapping("/personal-home")
	public ModelAndView getHome1(@RequestParam String id) {
		SystemConstant.id_user_current= Long.parseLong(id);
		ModelAndView model = new ModelAndView();
		model.setViewName("/personalpage");
		userService.getUserActive();
		List<UserDTO> users = new ArrayList<UserDTO>();
		try {
			DataInputStream din = new DataInputStream(SystemConstant.socket_client.getInputStream());
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
		
		model.addObject("users_active", users);
		String name = userService.findOne(Long.parseLong(id)).getFullname();
		model.addObject("name",name);
		model.addObject("id_user", id);
		
		
		documentService.findAllPersonal();
		List<DocumentDTO> list = documentTranfer.readDocumentClient();
		model.addObject("documents",list);


		return model;
	}

}
