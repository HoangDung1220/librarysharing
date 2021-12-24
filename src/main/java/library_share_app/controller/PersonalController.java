package library_share_app.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import library_share_app.constant.SystemConstant;
import library_share_app.dto.DocumentDTO;
import library_share_app.dto.UserDTO;
import library_share_app.service.impl.DocumentService;
import library_share_app.service.impl.DocumentUserService;
import library_share_app.service.impl.UserService;
import library_share_app.transfer.DocumentTransfer;
import library_share_app.transfer.UserTransfer;

@Controller
public class PersonalController extends BaseController{
	@Autowired
	private UserService userService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private DocumentUserService documentUserService;
	
	@Autowired
	private DocumentTransfer documentTranfer;
	
	@Autowired
	private UserTransfer userTranfer;

	@GetMapping("/personal-home")
	public ModelAndView getHome1(@RequestParam String id) {

		SystemConstant.id_user_current= Long.parseLong(id);
		model.setViewName("/personalpage");
		userService.getUserActive();
		List<UserDTO> users = userTranfer.readUserClient();
		model.addObject("users_active", users);

		String name = userService.findOne(Long.parseLong(id)).getFullname();
		model.addObject("name",name);
		model.addObject("id_user", id);
		documentService.findAllPersonal();
		List<DocumentDTO> list = documentTranfer.readDocumentClient(true);
		model.addObject("documents",list);
		DocumentDTO document = new DocumentDTO();
		model.addObject("document",document);

		return model;
	}
	
	@GetMapping("/sharing-document")
	public ModelAndView login(@RequestParam String id) {
		SystemConstant.id_user_current= Long.parseLong(id);
		String name = userService.findOne(Long.parseLong(id)).getFullname();
		ModelAndView model = new ModelAndView();
		documentService.findAllShared();
		List<DocumentDTO> list = documentTranfer.readDocumentClient(false);
		userService.getUserActive();
		List<UserDTO> users = userTranfer.readUserClient();
		model.addObject("users_active", users);
		model.addObject("documents",list);
		model.setViewName("/share_document_personal");
		model.addObject("name",name);
		model.addObject("id_user", id);

		return model;
	}
	
	@RequestMapping(value="/personal-home/deleteDocument", method= {RequestMethod.DELETE,RequestMethod.GET})
	public String deleteDocument(@RequestParam String id, @RequestParam String id1) {
		SystemConstant.id_user_current = Long.parseLong(id);
		Socket soc = documentTranfer.getSocketClient(Long.parseLong(id));
		DataOutputStream dos;
		try {
			dos = new DataOutputStream(soc.getOutputStream());
			dos.writeLong(Long.parseLong(id1));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		documentUserService.deleteDocumentUser();
		
		StringBuilder st = new StringBuilder("redirect:/personal-home?id=");
		st.append(id);
		return st.toString();
	}
	
	@GetMapping("/bin-document")
	public ModelAndView getBinHome(@RequestParam String id) {
		SystemConstant.id_user_current= Long.parseLong(id);
		ModelAndView model = new ModelAndView();
		model.setViewName("/bin_document_personal");
		userService.getUserActive();
		List<UserDTO> users = userTranfer.readUserClient();
		
		model.addObject("users_active", users);
		String name = userService.findOne(Long.parseLong(id)).getFullname();
		model.addObject("name",name);
		model.addObject("id_user", id);
		
		
		documentService.findAllDeletePersonal();
		List<DocumentDTO> list = documentTranfer.readDocumentClient(false);
		model.addObject("documents",list);


		return model;
	}
	
	@GetMapping("/personal-home/restoreDocument")
	public String restoreDocument(@RequestParam String id, @RequestParam String id1) {
		SystemConstant.id_user_current = Long.parseLong(id);
		Socket soc = documentTranfer.getSocketClient(Long.parseLong(id));
		DataOutputStream dos;
		try {
			dos = new DataOutputStream(soc.getOutputStream());
			dos.writeLong(Long.parseLong(id1));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		documentUserService.restoreDocumentUser();
		
		StringBuilder st = new StringBuilder("redirect:/bin-document?id=");
		st.append(id);
		return st.toString();
	}
	
	@GetMapping("/favourite")
	public ModelAndView getBinHome1(@RequestParam String id) {
		SystemConstant.id_user_current= Long.parseLong(id);
		ModelAndView model = new ModelAndView();
		model.setViewName("/favourite_personal");
		userService.getUserActive();
		List<UserDTO> users = userTranfer.readUserClient();
		model.addObject("users_active", users);
		String name = userService.findOne(Long.parseLong(id)).getFullname();
		model.addObject("name",name);
		model.addObject("id_user", id);
		
		
		documentService.findAllFavouritePersonal();
		List<DocumentDTO> list = documentTranfer.readDocumentClient(true);
		model.addObject("documents",list);


		return model;
	}
	
	@RequestMapping(value="/personal-home/importanceDocument", method= RequestMethod.GET)
	public String remarkDocument(@RequestParam String id, @RequestParam String id1) {
		SystemConstant.id_user_current = Long.parseLong(id);
		Socket soc = documentTranfer.getSocketClient(Long.parseLong(id));
		DataOutputStream dos;
		try {
			dos = new DataOutputStream(soc.getOutputStream());
			dos.writeLong(Long.parseLong(id1));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		documentUserService.importanceDocumentUser();
		
		StringBuilder st = new StringBuilder("redirect:/personal-home?id=");
		st.append(id);
		return st.toString();
	}
	
	@RequestMapping(value="/personal-home/unimportanceDocument", method= RequestMethod.GET)
	public String unremarkDocument1(@RequestParam String id, @RequestParam String id1) {
		SystemConstant.id_user_current = Long.parseLong(id);
		Socket soc = documentTranfer.getSocketClient(Long.parseLong(id));
		DataOutputStream dos;
		try {
			dos = new DataOutputStream(soc.getOutputStream());
			dos.writeLong(Long.parseLong(id1));

		} catch (IOException e) {
			e.printStackTrace();
		}
		documentUserService.unremarkDocument();		
		StringBuilder st = new StringBuilder("redirect:/personal-home?id=");
		st.append(id);
		return st.toString();
	}
	
	@GetMapping("/unremarkDocument")
	public String unremarkDocument(@RequestParam String id, @RequestParam String id1) {
		SystemConstant.id_user_current = Long.parseLong(id);
		Socket soc = documentTranfer.getSocketClient(Long.parseLong(id));
		DataOutputStream dos;
		try {
			dos = new DataOutputStream(soc.getOutputStream());
			dos.writeLong(Long.parseLong(id1));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		documentUserService.unremarkDocument();
		
		StringBuilder st = new StringBuilder("redirect:/favourite?id=");
		st.append(id);
		return st.toString();
	}
	
	@PostMapping("/document_user/addDocument")
	public String saveDocument(@ModelAttribute("document") DocumentDTO document,@RequestParam("id") String id1) {
		SystemConstant.id_user_current = Long.parseLong(id1);
		Socket soc = null;
		soc = documentTranfer.getSocketClient(Long.parseLong(id1));

		try {
			DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
			dos.writeUTF(document.getDisplayFileName());
			dos.writeUTF(document.getDescription());
			dos.writeUTF(document.getFileName());
			dos.writeBoolean(false);
			dos.writeLong(document.getId_Category());
			dos.writeLong(Long.parseLong(id1));
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
		
		documentService.save();
		StringBuilder st = new StringBuilder("redirect:/personal-home?id=");
		st.append(id1);
		return st.toString();
	}
	
	@PostMapping("/personal-home/shareDocument")
	public String shareDocument(@RequestParam("id") String id_user,@RequestParam("id1") String id_doc,@ModelAttribute("email") String email) {
		SystemConstant.id_user_current = Long.parseLong(id_user);
		Socket soc = null;
		soc = documentTranfer.getSocketClient(Long.parseLong(id_user));

		try {
			DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
			dos.writeLong(Long.parseLong(id_user));
			dos.writeLong(Long.parseLong(id_doc));
			dos.writeUTF(email);
		} catch (IOException e) {
			e.printStackTrace();
		}
		documentUserService.shareDocument();
		StringBuilder st = new StringBuilder("redirect:/personal-home?id=");
		st.append(id_user);
		return st.toString();
	}
	
	@GetMapping("/chat")
	public String chat() {
		return "chat";
	}
	
	@PostMapping("/search_user")
	public ModelAndView getHomePage1(@RequestParam("id") String id, @ModelAttribute("character") String character) {
		model.setViewName("/personalpage");
		model.addObject("id_user", id);
		DocumentDTO document = new DocumentDTO();
		model.addObject("document",document);
		SystemConstant.id_user_current=Long.parseLong(id);
		Socket socket = documentTranfer.getSocketClient(Long.parseLong(id));
		try {
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(character);
		} catch (IOException e) {
			e.printStackTrace();
		}
		documentService.findByIdAndDisplayFileNameContaining(Long.parseLong(id));
		List<DocumentDTO> list = documentTranfer.readDocumentClient1(socket, true);
		model.addObject("documents",list);
		String name = userService.findOne(Long.parseLong(id)).getFullname();
		model.addObject("name",name);
		return model;
	}
	
	@GetMapping("/download")
	public String download(@RequestParam("id") String id,@RequestParam("file-name") String filename) {
		System.out.println(filename);
		
		SystemConstant.id_user_current=Long.parseLong(id);
		Socket soc = documentTranfer.getSocketClient(Long.parseLong(id));
		
		
		try {
			DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
			dos.writeUTF(filename);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		documentService.download();
		
		try {
			DataInputStream din = new DataInputStream(soc.getInputStream());

			long fileSize = din.readLong();
			fileReceive(fileSize,filename,din);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		StringBuilder st = new StringBuilder("redirect:/personal-home?id=");
		st.append(id);
		return st.toString();
			
	}
	
	public void fileReceive(long fileSize,String file_name,DataInputStream din) throws IOException {
		StringBuilder source = new StringBuilder();
		source.append(SystemConstant.download_server+file_name);
		File f = new File(source.toString());
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(f));
			
			for (int i = 0; i < fileSize; i++) {
				bos.write(din.read());
				bos.flush();
			}
			bos.close();
		} catch (IOException e) {
		}
	}
	
	

}
