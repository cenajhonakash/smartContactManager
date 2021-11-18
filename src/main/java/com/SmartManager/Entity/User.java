package com.SmartManager.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
//import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "User")
public class User {

	@Id

	@GeneratedValue(strategy = GenerationType.AUTO) 
	private int id; 
	@NotBlank(message = "name should not be empty")
	@Size(max=12, min = 3, message = "Please enter name between 3-12 characters")
	private String name; 
	private String pass; 
	private String role;

	@Column(length = 500) private String about;

	//@Email(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$") 
	@Column(unique = true) 
	private String email; 
	
	private String imageURL; 
	private boolean enabled;

	//mappedBy = "user" to avoid unnecessary table [user_contact_list] creation for Foreign Key Mapping of User & Contact Table + It'll create one 'userId' column and we have to explicitly map the user with contact
	//CascadeType.ALL = Used to delete everything related tp user like his contactlist
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user") private
	List<Contact> contactList = new ArrayList<>();

	public User() { super();} // TODO Auto-generated constructor stub }

	public List<Contact> getContactList() { return contactList; }

	public void setContactList(List<Contact> contactList) { this.contactList =
			contactList; }

	public String getName() { return name; } public void setName(String name) {
		this.name = name; } public int getId() { return id; } public void setId(int
				id) { this.id = id; } public String getPass() { return pass; } public void
	setPass(String pass) { this.pass = pass; } public String getRole() { return
			role; } public void setRole(String role) { this.role = role; } public String
	getAbout() { return about; } public void setAbout(String about) { this.about
		= about; } public String getEmail() { return email; } public void
	setEmail(String email) { this.email = email; } public String getImageURL() {
		return imageURL; } public void setImageURL(String imageURL) { this.imageURL =
				imageURL; } public boolean getEnabled() { return enabled; } public void
	setEnabled(boolean enabled) { this.enabled = enabled; }


				@Override
				public String toString() {
					return "User [id=" + id + ", name=" + name + ", pass=" + pass + ", role=" + role + ", about="
							+ about + ", email=" + email + ", imageURL=" + imageURL + ", enabled=" + enabled
							+ ", contactList=" + contactList + "]";
				}


	}
