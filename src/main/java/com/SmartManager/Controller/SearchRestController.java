package com.SmartManager.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.SmartManager.DaoTest.ContactRepo;
import com.SmartManager.DaoTest.UserRepo;
import com.SmartManager.Entity.Contact;

@RestController
public class SearchRestController {

	@Autowired
	private UserRepo uR;
	@Autowired
	private ContactRepo cR;
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?> searchController(@PathVariable("query") String query,Principal p){
		System.out.println("Query for search = "+query);
		List<Contact> contacts = this.cR.findByNameContainingAndUser(query, this.uR.getUserbyUsername(p.getName()));
		return ResponseEntity.ok(contacts);
	}

}
