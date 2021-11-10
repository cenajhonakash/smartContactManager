package com.SmartManager.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Contact")
public class Contact {


	@Id

	@GeneratedValue(strategy = GenerationType.AUTO) 
	private int contactId;
	private int number; 
	private String imageUrl; 
	private String name;
	@Column(length = 500) 
	private String description;
	private String work; 
	private String nickname;

	@ManyToOne() private User user;

	public int getContactId() { return contactId; } public void setContactId(int
			contactId) { this.contactId = contactId; } public int getNumber() { return
					number; } public void setNumber(int number) { this.number = number; } public
	String getImageUrl() { return imageUrl; } public void setImageUrl(String
			imageUrl) { this.imageUrl = imageUrl; } public String getName() { return
					name; } public void setName(String name) { this.name = name; } public String
	getDescription() { return description; } public void setDescription(String
			description) { this.description = description; } public String getWork() {
				return work; } public void setWork(String work) { this.work = work; } public
	String getNickname() { return nickname; } public void setNickname(String
			nickname) { this.nickname = nickname; } public Contact() { super();}


}
