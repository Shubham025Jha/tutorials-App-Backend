package com.tutorials.practice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Table(name="Users")
@Entity
public class User {
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "UserId")
	private int id;
	
	@Column
	private String emailId;
	@Column
	private String userName;
	@Column
	private String password;
	@Column
	private boolean admin;
	
	public User( String emailId, String userName, String password, boolean admin) {
		super();
		
		this.emailId = emailId;
		this.userName = userName;
		this.password = password;
		this.admin = admin;
	}
	
	public User() {
		
	}
	@Override
	public String toString() {
		return "User [emailId=" + emailId + ", userName=" + userName + ", password=" + password + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
	
}
