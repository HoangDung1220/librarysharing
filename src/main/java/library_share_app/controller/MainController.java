package library_share_app.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import library_share_app.constant.SystemConstant;
import library_share_app.dto.UserDTO;
import library_share_app.service.impl.UserService;

@Controller
public class MainController extends BaseController {
	

	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/main-home")
	public String getHome(Model model) {
		Thread t = new Thread() {
			
			@Override
			public void run() {
				try {
					while (true) {
					SystemConstant.socket = SystemConstant.server.accept();
					SystemConstant.sockets.add(SystemConstant.socket);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}				
		};
		t.start();
		model.addAttribute("id_user", "0");
		return "mainPage";
	}

	@GetMapping("/login")
	public String login() {

		try {
			SystemConstant.socket_client = new Socket("localhost",2000);			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "login";
	}
	
	@PostMapping("/main")
	public String login1(@ModelAttribute("username") String username , @ModelAttribute("password") String password,Model model) {
		
		try {
			DataOutputStream dos = new DataOutputStream(SystemConstant.socket_client.getOutputStream());
			dos.writeUTF(username);
			dos.writeUTF(password);
			Thread t = new Thread() {
				@Override
				public void run() {
					userService.findOneByUsernameAndPassword();
				}
			};
			t.start();
			
			DataInputStream din = new DataInputStream(SystemConstant.socket_client.getInputStream());
			Long id= din.readLong();
			String fullname=din.readUTF();
			String nameRoom=din.readUTF();
			String gmail=din.readUTF();
			String username1=din.readUTF();
			String password1=din.readUTF();
			UserDTO user = new UserDTO(id, fullname, gmail, nameRoom, username1, password1, true);
			if (user!=null) {
				SystemConstant.list_user_active.put(user,SystemConstant.socket);
				SystemConstant.list_socket_client.put(user,SystemConstant.socket_client);
				model.addAttribute("id_user", user.getId());
				return "mainPage";
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "login";
	}

}
