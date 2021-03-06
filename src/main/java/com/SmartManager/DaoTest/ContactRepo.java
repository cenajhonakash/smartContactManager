package com.SmartManager.DaoTest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;

import com.SmartManager.Entity.Contact;
import com.SmartManager.Entity.User;

public interface ContactRepo extends JpaRepository<Contact, Integer>{
	@Query("from Contact as con where con.user.id =:id")
	public Page<Contact> getContactByUserID(@Param("id") int id, Pageable pageable);
	
	//search data provider
	public List<Contact> findByNameContainingAndUser(String keyword, User user);
	
}
