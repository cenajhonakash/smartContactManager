package com.SmartManager.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SmartManager.DaoTest.UserRepo;
import com.SmartManager.Entity.Contact;
import com.SmartManager.Entity.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepo uR;

	//method to use common User for every html
	@ModelAttribute
	public void addCommonData(Model m, Principal p) {
		String username = p.getName();
		User u = uR.getUserbyUsername(username);
		m.addAttribute("user", u);
	}

	@RequestMapping("/index")
	public String dashoard(Model m)
	{
		/*
		 * String username = p.getName(); User u = uR.getUserbyUsername(username);
		 * 
		 */
		m.addAttribute("title", "User|Dashboard");
		return "User/userDashboard";
	}

	@GetMapping("/addContact")
	public String openAddContactForm(Model m)
	{
		m.addAttribute("title", "AddContact");
		m.addAttribute("contact", new Contact());
		return "User/addContact";
	}

	@PostMapping("/processContact")
	public String processContact(@ModelAttribute Contact c, Principal p)
	{
		User u=this.uR.getUserbyUsername(p.getName());
		System.out.println(u);
		u.getContactList().add(c);
		this.uR.save(u);
		System.out.println("Contact = "+c);
		return "User/addContact";	
	}
}
