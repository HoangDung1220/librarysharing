package library_share_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonalController {

	@GetMapping("/personal-home")
	public String getHome() {
		return "personalpage";
	}
}
