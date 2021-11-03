package com.SmartManager.DaoTest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SmartManager.Entity.User;
/*
public interface UserRepo{
}
*/

public interface UserRepo extends JpaRepository<User, Integer>{
  
	@Query("select u from User u where u.email = :email")
	public User getUserbyUsername(@Param("email") String userName);
  }
