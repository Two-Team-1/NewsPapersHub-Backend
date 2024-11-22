package com.NewsPaperHub.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "loginpage")

public class Loginpage {
	
	@Id
	@Column
	private String userName;
	
	@Column
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getpassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	

	

}
