package library_share_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import library_share_app.constant.SystemConstant;

@Controller
public class PersonalController {

	@GetMapping("/personal-home")
	public String getHome() {
		System.out.println(SystemConstant.list_user_active);
		return "personalpage";
	}
}
