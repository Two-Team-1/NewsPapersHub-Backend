package com.NewsPaperHub.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.NewsPaperHub.Entity.Loginpage;
import com.NewsPaperHub.Repository.LoginRepository;



@Service
public class LoginpageService {
	
	@Autowired
	LoginRepository loginRepositary;
	

	public List<Loginpage> getAll(Loginpage loginpage) {
		return loginRepositary.findAll();
	}

	
	
	public Loginpage update(Loginpage loginpage) {
		Loginpage login=loginRepositary.findById(loginpage.getUserName()).get();
		login.setPassword(loginpage.getpassword());
		
		return loginRepositary.save(login);

	}
	public void  delete(Loginpage loginpage) {
		loginRepositary.deleteById(loginpage.getUserName());
		
		
		
	}
	public Optional<Loginpage> findByUserName(String username) {
		
		return loginRepositary.findByuserName(username);
	}
	
	

}
