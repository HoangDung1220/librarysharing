package library_share_app.controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import library_share_app.constant.SystemConstant;
import library_share_app.dto.DocumentDTO;
import library_share_app.dto.UserDTO;
import library_share_app.service.impl.CategoryService;
import library_share_app.service.impl.DocumentService;
import library_share_app.service.impl.UserService;
import library_share_app.transfer.DocumentTransfer;

@Controller
public class GeneralController extends BaseController{
	@Autowired
	private DocumentService service;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DocumentTransfer documentTransfer;
	
	
	@GetMapping("/general-home")
	public ModelAndView getHomePage(@RequestParam("id") String id) {
		model.setViewName("/publicpage");
		model.addObject("id_user", id);
		List<DocumentDTO> documents = new ArrayList<DocumentDTO>();
		DocumentDTO document = new DocumentDTO();
		model.addObject("document",document);
		System.out.println("head controller");
		SystemConstant.id_user_current=Long.parseLong(id);

		service.findAll();

		
		Socket soc = null;
		 for (Map.Entry<UserDTO, Socket> item : SystemConstant.list_socket_client.entrySet()) {
			 if (item.getKey().getId()==Long.parseLong(id)) {
				soc = item.getValue(); 
			 }
	       } 
		try {
				DataInputStream din = new DataInputStream(soc.getInputStream());
				int list_size = din.readInt();
				
				System.out.println("conroller: "+list_size);
				for (int i=1;i<=list_size;i++)
				{	
					DocumentDTO dto = new DocumentDTO();
					dto.setDisplayFileName(din.readUTF());
					dto.setDescription(din.readUTF());
					dto.setFileName(din.readUTF());
					String[] d = din.readUTF().split(" ");
				
					  
					 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
					    try {  
					        Date date = formatter.parse(d[0]);  
					        dto.setSharedDate(date);
					    } catch (ParseException e) {e.printStackTrace();}  
					    
					 
					dto.setSizeFile(din.readUTF());
					dto.setStatus(din.readBoolean());
					dto.setId_Category(din.readLong());
					dto.setId_user(din.readLong());
					dto.setId(din.readLong());
					dto.setCategory(categoryService.findOneById(dto.getId_Category()));
					dto.setUser(userService.findOne(dto.getId_user()));
					documents.add(dto);	
				}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addObject("documents",documents);
		String name = userService.findOne(Long.parseLong(id)).getFullname();
		model.addObject("name",name);
		return model;
		
	}
	
	
	
	@PostMapping("/document/addDocument")
	public String saveDocument(@ModelAttribute("document") DocumentDTO document,@RequestParam("id") String id1) {
		SystemConstant.id_user_current = Long.parseLong(id1);
		Socket soc = null;
		 for (Map.Entry<UserDTO, Socket> item : SystemConstant.list_socket_client.entrySet()) {
			 if (item.getKey().getId()==Long.parseLong(id1)) {
				soc = item.getValue(); 
			 }
	       } 
		
		try {
			System.out.println("client add");
			DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
			dos.writeUTF(document.getDisplayFileName());
			dos.writeUTF(document.getDescription());
			dos.writeUTF(document.getFileName());
			dos.writeBoolean(document.isStatus());
			dos.writeLong(document.getId_Category());
			dos.writeLong(Long.parseLong(id1));
			StringBuilder sourcefile = new StringBuilder();
			sourcefile.append(SystemConstant.upload+"\\"+document.getFileName());
			File fileSource = new File(sourcefile.toString());
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileSource));
			dos.writeLong(fileSource.length());
			dos.writeUTF(document.getEmailShare());
			dos.flush();
				int data;
				while ((data = bis.read()) != -1) {
					dos.write(data);
					dos.flush();
				}
				bis.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		service.save();
		StringBuilder st = new StringBuilder("redirect:/general-home?id=");
		st.append(id1);
		return st.toString();
	}
	
	@GetMapping("/document_category")
	public ModelAndView getHomePage(@RequestParam("id") String id,@RequestParam("id_category") String id_category) {
	
		SystemConstant.id_user_current=Long.parseLong(id);
		service.findAllByCategory(Long.parseLong(id_category));
		List<DocumentDTO> documents = documentTransfer.readDocumentClient();
		String name = userService.findOne(Long.parseLong(id)).getFullname();
		String name_category = categoryService.findOneById(Long.parseLong(id_category)).getName();
		model.addObject("documents",documents);
		model.addObject("name",name);
		model.addObject("name_category",name_category);
		model.setViewName("/public_page_category");
		model.addObject("id_user", id);
		return model;
		
	}
	
	

}
