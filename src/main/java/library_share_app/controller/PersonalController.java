package library_share_app.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
public class PersonalController {
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
		ModelAndView model = new ModelAndView();
		model.setViewName("/personalpage");
		userService.getUserActive();
		List<UserDTO> users = userTranfer.readUserClient();
		model.addObject("users_active", users);
		String name = userService.findOne(Long.parseLong(id)).getFullname();
		model.addObject("name",name);
		model.addObject("id_user", id);
		
		
		documentService.findAllPersonal();
		List<DocumentDTO> list = documentTranfer.readDocumentClient();
		model.addObject("documents",list);


		return model;
	}
	
	@GetMapping("/sharing-document")
	public ModelAndView login(@RequestParam String id) {
		SystemConstant.id_user_current= Long.parseLong(id);
		String name = userService.findOne(Long.parseLong(id)).getFullname();
		ModelAndView model = new ModelAndView();
		documentService.findAllShared();
		List<DocumentDTO> list = documentTranfer.readDocumentClient();
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
		List<DocumentDTO> list = documentTranfer.readDocumentClient();
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
		List<DocumentDTO> list = documentTranfer.readDocumentClient();
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

}
