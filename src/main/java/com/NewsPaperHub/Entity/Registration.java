package com.NewsPaperHub.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="registration")

public class Registration {
	
	@Column
	private String fullName;
	@Id
	@Column
	private String email;
	@Column
	private String password;
	@Column
	private String phoneNumber;
	
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhonenumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
