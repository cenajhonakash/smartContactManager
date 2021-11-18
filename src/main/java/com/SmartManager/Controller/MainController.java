package com.SmartManager.Controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.SmartManager.DaoTest.Messages;
import com.SmartManager.DaoTest.UserRepo;
import com.SmartManager.Entity.User;

@Controller
public class MainController {

	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private UserRepo urepo;

	@RequestMapping("/home")
	//@ResponseBody
	public String home(Model model)
	{
		/*
		 * User u1 = new User(); u1.setName("Akash"); u1.setPass("Akash@19");
		 * u1.setRole("IAS");
		 * 
		 * urepo.save(u1); return "Working"
		 */
		model.addAttribute("title", "Home - Smart COmtact");
		return "Home";
	}

	@RequestMapping("/about")
	public String about(Model model)
	{
		/*
		 * User u1 = new User(); u1.setName("Akash"); u1.setPass("Akash@19");
		 * u1.setRole("IAS");
		 * 
		 * urepo.save(u1); return "Working"
		 */
		model.addAttribute("title", "About - Smart Contact");
		return "about";
	}

	@RequestMapping("/signup")
	public String signUp(Model model)
	{
		/*
		 * User u1 = new User(); u1.setName("Akash"); u1.setPass("Akash@19");
		 * u1.setRole("IAS");
		 * 
		 * urepo.save(u1); return "Working"
		 */
		model.addAttribute("title", "Register - Smart COmtact");
		model.addAttribute("user", new User());	//to make the user entered values visible on browser during runtime
		return "signup";
	}

	//@RequestParam(value="Agreement", defaultValue = "false") Boolean agreement = to check whether T&C are agreed or not
	//@ModelAttribute("user") User user = to store the user data via register page in DB
	//Model model = to display data on web page
	@RequestMapping(value="/do_register", method=RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, 
			@RequestParam(value="agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session)
	{
		try {
			if(!agreement)
			{
				System.out.println("T&C not agreed...");
				throw new Exception("Please agree to the T&C!!!!");
			}
			if(result.hasErrors())
			{
				System.out.println("server side validation error : "+result.toString());
				model.addAttribute("user", user);
				return "signup";
			}
			user.setImageURL("Default img");
			user.setEnabled(true);
			user.setRole("ROLE_USER");
			user.setPass(encoder.encode(user.getPass()));		
			//System.out.println("Agreement = "+agreement);
			//System.out.println("USER = "+ user);

			this.urepo.save(user);
			//model.addAttribute(user);
			model.addAttribute("user", new User());
			session.setAttribute("message", new Messages("Successfully registered ","alert-success"));
			return "signup";
		} catch (Exception e) {
			
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Messages("something went wrong "+e.getMessage(),"alert-danger"));
			return "signup";
		}

	}

	@GetMapping("/signin")
	public String userSignin(Model model)
	{
		model.addAttribute("title", "Login Page");
		return "login";
	}

}
