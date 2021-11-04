package com.SmartManager.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.SmartManager.DaoTest.UserRepo;
import com.SmartManager.Entity.User;

public class CustomUserDetailServImpl implements UserDetailsService{

	@Autowired
	private UserRepo uRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = uRepo.getUserbyUsername(username);
		if(u==null) {
			System.out.println("User not found in Database!!");
			System.out.println("Email = "+username);
			System.out.println("Pass = ");
			throw new UsernameNotFoundException("User not found in Database!!");

		}
		CustomUserDetails cUd = new CustomUserDetails(u);
		return cUd;
	}

}
