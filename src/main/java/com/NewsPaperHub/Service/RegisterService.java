package com.NewsPaperHub.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.NewsPaperHub.Entity.Loginpage;
import com.NewsPaperHub.Entity.Registration;
import com.NewsPaperHub.Repository.LoginRepository;
import com.NewsPaperHub.Repository.RegistrationReposotory;

import jakarta.mail.internet.MimeMessage;

@Service
public class RegisterService {
	
	
	@Autowired
	RegistrationReposotory registerRepo;
	@Autowired
	LoginRepository loginRepositary;
	 @Autowired
	 private JavaMailSender javaMailSender;

	 @Value("${spring.mail.username}")
	 private String sender;


	
	public Registration addregister(Registration register) {
		
		
		Registration reg=registerRepo.save(register);
		  Loginpage userlogin = new Loginpage();

		 userlogin.setUserName(reg.getEmail());
		 userlogin.setPassword(reg.getPassword());
		loginRepositary.save(userlogin);
		
		return registerRepo.save(register);
	}
	public List<Registration> getAll() {
		return registerRepo.findAll();
	}
	
	public boolean check(String username,String password) 
	{
		Registration reg=registerRepo.findByEmail(username);
		if(reg==null)
		{
			return false;
		}
		
		return reg.getPassword().equals(password);
	}
	

	public Registration getById(String email) {
		return registerRepo.findByEmail(email);
	}
	public Registration findByEmail(String email) {
		
		return registerRepo.findByEmail(email);
	}
	public Registration update(Registration register, String email) {
		
		Registration reg=registerRepo.findByEmail(email);
		reg.setFullName(register.getFullName());
		reg.setPhonenumber(register.getPhoneNumber());
		reg.setEmail(register.getEmail());
	 Loginpage login=loginRepositary.findByUserName(email);
		 
		login.setUserName(register.getEmail());
		loginRepositary.save(login);
		
		
		
		return registerRepo.save(reg);
	}
	 
	
	public void sendWelcomeEmail(Registration register) {
	    String emailContent = "<p>Dear " + register.getFullName() + ",</p>" +
	                          "<p>Welcome to Newspapers Hub!</p>" +
	                          "<p>Your registration was successful. We're excited to have you onboard.</p>" +
	                          "<p>Regards,<br>Newspapers Hub Team</p>";

	    try {
	        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
	        
	   
	        helper.setText(emailContent, true); 
	        helper.setTo(register.getEmail()); 
	        helper.setSubject("Welcome to Newspapers Hub");
	        helper.setFrom(sender);

	        
	        javaMailSender.send(mimeMessage);
	    } catch (Exception e) {
	        throw new RuntimeException("Error sending welcome email: " + e.getMessage());
	    }
	}

	public boolean checkMail(String email) {
	    Optional<Registration> register = Optional.ofNullable(registerRepo.findByEmail(email));
	    
	    
	    if (register.isEmpty()) {
	        return false;
	    }
	    return true;
	}

	
}
