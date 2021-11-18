package com.SmartManager.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.SmartManager.DaoTest.Messages;
import com.SmartManager.DaoTest.UserRepo;
import com.SmartManager.Entity.Contact;
import com.SmartManager.Entity.User;
import com.SmartManager.Utility.FileUploadService;

@Controller
@RequestMapping("/user")
public class UserController {

	//@Autowired
	//FileUploadService fileUpload;
	@Autowired
	private UserRepo uR;

	// method to use common User for every html
	@ModelAttribute
	public void addCommonData(Model m, Principal p) {
		String username = p.getName();
		User u = uR.getUserbyUsername(username);
		m.addAttribute("user", u);
	}

	@RequestMapping("/index")
	public String dashoard(Model m) {
		/*
		 * String username = p.getName(); User u = uR.getUserbyUsername(username);
		 * 
		 */
		m.addAttribute("title", "User|Dashboard");
		return "User/userDashboard";
	}

	@GetMapping("/addContact")
	public String openAddContactForm(Model m) {
		m.addAttribute("title", "AddContact");
		m.addAttribute("contact", new Contact());
		return "User/addContact";
	}

	@RequestMapping(value = "/processContact", method = RequestMethod.POST)
	public String processContact(@ModelAttribute Contact c, @RequestPart("image") MultipartFile file ,Principal p, HttpSession s)
	{
		System.out.println(c);
		try {
			System.out.println("Contact = "+c);
			User u=this.uR.getUserbyUsername(p.getName());
			System.out.println("User = "+u);
			//
			if(file.isEmpty()) {
				System.out.println("No filr choosen!!");
				return"User/addContact";
			}
			else {
				c.setImageUrl(file.getOriginalFilename());

				File save = new ClassPathResource("/static/IMG/UploadedIMG").getFile(); Path
				path = Paths.get(save.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				//fileUpload.uploadFile(file);
				s.setAttribute("message", new Messages("Contact added successfully!!!", "success"));
				System.out.println("Image Uploaded");
				c.setUser(u);
				//System.out.println("Contact = "+c);
				u.getContactList().add(c);

				this.uR.save(u);
				//System.out.println("Contact = "+c);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			s.setAttribute("message", new Messages("Oops!!! Something went wrong. Try again", "danger"));
			e.printStackTrace();
		}

		return"User/addContact";
	}
}
