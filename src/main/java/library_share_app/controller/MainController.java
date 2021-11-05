package library_share_app.controller;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import library_share_app.constant.SystemConstant;
import library_share_app.service.impl.DocumentService;

@Controller
public class MainController {
	
	@Autowired
	private DocumentService service;
	
	
	@GetMapping("/main-home")
	public String getHome() {
		System.out.println("hello");
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
		service.findAll();
		return "login";
	}

}
