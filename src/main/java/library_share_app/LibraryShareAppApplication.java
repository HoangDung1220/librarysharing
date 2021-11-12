package library_share_app;

import java.io.IOException;
import java.net.ServerSocket;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import library_share_app.constant.SystemConstant;

@SpringBootApplication
public class LibraryShareAppApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	} 
	
	@Bean
	public ServerSocket serverSocket() {
		try {
			SystemConstant.server= new ServerSocket(2000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SystemConstant.server;
	} 
	
	public static void main(String[] args) {
		SpringApplication.run(LibraryShareAppApplication.class, args);
	}




	
	

}
