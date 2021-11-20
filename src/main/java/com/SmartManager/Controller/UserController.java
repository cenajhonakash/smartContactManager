package com.SmartManager.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.SmartManager.DaoTest.ContactRepo;
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
	@Autowired
	private ContactRepo cR;

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
		m.addAttribute("title", "User | Dashboard");
		return "User/userDashboard";
	}

	@GetMapping("/addContact")
	public String openAddContactForm(Model m) {
		m.addAttribute("title", "AddContact");
		m.addAttribute("con", new Contact());
		return "User/addContact";
	}

	@RequestMapping(value = "/processContact", method = RequestMethod.POST)
	public String processContact(@ModelAttribute Contact con,  @RequestPart("image") MultipartFile file ,Principal p, HttpSession s, Model m)
	{
		//System.out.println(c);
		m.addAttribute("title", "AddContact");

		try {
			//System.out.println("Contact = "+c);
			User u=this.uR.getUserbyUsername(p.getName());
			//	System.out.println("User = "+u);
			//
			if(file.isEmpty()) {
				m.addAttribute("con", con);
				System.out.println("No filr choosen!!");
				s.setAttribute("message", new Messages("Please choose a image!!!", "danger"));
				return"User/addContact";
			}
			else {
				con.setImageUrl(file.getOriginalFilename());

				//Image upload into Target Folder
				File save = new ClassPathResource("/static/IMG/UploadedIMG").getFile(); Path
				path = Paths.get(save.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				//fileUpload.uploadFile(file);
				s.setAttribute("message", new Messages("Contact added successfully!!!", "success"));
				System.out.println("Image Uploaded");
				con.setUser(u);
				//System.out.println("Contact = "+c);
				u.getContactList().add(con);

				this.uR.save(u);
				//System.out.println("Contact = "+c);
				m.addAttribute("con", new Contact());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			m.addAttribute("con", con);
			s.setAttribute("message", new Messages("Oops!!! Something went wrong. Try again", "danger"));
			e.printStackTrace();
		}

		return"User/addContact";
	}

	//@PathVariable annotation can be used to handle template variables in the request URI mapping, and set them as method parameters
	@GetMapping("/contactList/{page}")
	public String getContactListOfUser(@PathVariable("page") Integer page ,Model m, Principal p) {
		m.addAttribute("title", "User | Contact List");
		Pageable pageable = PageRequest.of(page, 4);
		Page<Contact> contactList = this.cR.getContactByUserID(this.uR.getUserbyUsername(p.getName()).getId(), pageable);
		System.out.println(contactList.toString());
		m.addAttribute("cList", contactList);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPage", contactList.getTotalPages());
		return "User/contactList";
	}

	@GetMapping("/contactList/showProfile/{contactId}")
	public String showProfile(@PathVariable("contactId") Integer contactId, Model m, Principal p){
		Optional<Contact> cOpt = this.cR.findById(contactId);
		Contact c = cOpt.get();
		String user = p.getName();
		User u = this.uR.getUserbyUsername(user);

		if(u.getId() == c.getUser().getId()) {
			m.addAttribute("con", c);
		}else {
			System.out.println("Unauthorized!!!! URL Hit & Trial used");
		}

		m.addAttribute("title", "User | Contact-Profile");
		return "User/contactDetail";
	}
	//c.setUser(null); = because while mapping user with contact cascade = CascadeType.ALL which didn't allow to delete contact before deleting user
	@GetMapping("/deleteContact/{contactId}")
	public String deleteContact(@PathVariable("contactId") Integer contactId, Model m, Principal p, HttpSession session) {
		System.out.println("contact id = "+contactId);
		Optional<Contact> cOpt = this.cR.findById(contactId);
		Contact c = cOpt.get();
		System.out.println("Contact = "+ c);
		String user = p.getName();
		User u = this.uR.getUserbyUsername(user);

		if(u.getId() == c.getUser().getId()) {
			System.out.println("User and Contact id verified");
			c.setUser(null);
			this.cR.delete(c);
			session.setAttribute("message", new Messages("Contact deleted", "success"));

			//m.addAttribute("con", c);
		}else {
			session.setAttribute("message", new Messages("Contact not deleted!!..Unauthorized Deletion performed", "danger"));
			System.out.println("Unauthorized!!!! URL Hit & Trial used");
		}

		return "redirect:/user/contactList/0";
	}
}
