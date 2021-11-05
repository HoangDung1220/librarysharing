package library_share_app.controller;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import library_share_app.constant.SystemConstant;
import library_share_app.dto.DocumentDTO;
import library_share_app.service.impl.CategoryService;
import library_share_app.service.impl.DocumentService;

@Controller
public class GeneralController extends BaseController{
	@Autowired
	private DocumentService service;
	
	@Autowired
	private CategoryService categoryService;
	
	
	@GetMapping("/general-home")
	public ModelAndView getHomePage() {
		model.setViewName("/publicpage");
		
		List<DocumentDTO> documents = new ArrayList<DocumentDTO>();
		DocumentDTO document = new DocumentDTO();
		model.addObject("document",document);
		Thread t = new Thread() {

			@Override
			public void run() {
				service.findAll();
			}
		};
		t.start();
		
		try {
				DataInputStream din = new DataInputStream(SystemConstant.socket_client.getInputStream());
				int list_size = din.readInt();
				for (int i=1;i<=list_size;i++)
				{	
					DocumentDTO dto = new DocumentDTO();
					dto.setDisplayFileName(din.readUTF());
					dto.setDescription(din.readUTF());
					dto.setFileName(din.readUTF());
				//	dto.setSharedDate(Date.valueOf(din.readUTF()));
					dto.setSizeFile(din.readUTF());
					dto.setStatus(din.readBoolean());
					dto.setId_Category(din.readLong());
					dto.setId(din.readLong());
					dto.setCategory(categoryService.findOneById(dto.getId_Category()));
					documents.add(dto);	
				}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addObject("documents",documents);
		return model;
	}
	
	@GetMapping("/general-home_1")
	public ModelAndView getHomePage1() {
		model.setViewName("/publicpage");
		DocumentDTO document = new DocumentDTO();
		model.addObject("document",document);
		return model;
	}
	
	@PostMapping("/document/addDocument")
	public String saveDocument(@ModelAttribute("document") DocumentDTO document) {
		
		try {
			DataOutputStream dos = new DataOutputStream(SystemConstant.socket_client.getOutputStream());
			dos.writeUTF(document.getDisplayFileName());
			dos.writeUTF(document.getDescription());
			dos.writeUTF(document.getFileName());
			dos.writeBoolean(document.isStatus());
			dos.writeLong(document.getId_Category());
			StringBuilder sourcefile = new StringBuilder();
			sourcefile.append(SystemConstant.upload+"\\"+document.getFileName());
			File fileSource = new File(sourcefile.toString());
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileSource));
			dos.writeLong(fileSource.length());
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
		Thread t = new Thread() {
			@Override
			public void run() {
				service.save();
			}
		};
		t.start();
		
		return "redirect:/general-home";
	}
	
	

}
