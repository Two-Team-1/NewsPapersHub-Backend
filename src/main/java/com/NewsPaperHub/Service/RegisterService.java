package com.NewsPaperHub.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.NewsPaperHub.Entity.Loginpage;
import com.NewsPaperHub.Entity.Registration;
import com.NewsPaperHub.Repository.LoginRepository;
import com.NewsPaperHub.Repository.RegistrationReposotory;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class RegisterService {
	//private String customerId;
	
	@Autowired
	RegistrationReposotory registerRepo;
	@Autowired
	LoginRepository loginRepositary;


	
	public Registration addregister(Registration register) {
	//	customerId=generateOTP(6);
		
		
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
		//reg.setAddress(register.getAddress());
		reg.setPhonenumber(register.getPhoneNumber());
		reg.setEmail(register.getEmail());
	 Loginpage login=loginRepositary.findByUserName(email);
		 
		login.setUserName(register.getEmail());
		loginRepositary.save(login);
		
		
		
		return registerRepo.save(reg);
	}
	 
	public Registration updateUserEmail(String currentEmail, String newEmail, Registration updatedRegistration) throws Exception {
	        try {
	            Registration existingRegistrationWithEmail = registerRepo.findByEmail(newEmail);

	            if (existingRegistrationWithEmail != null && !existingRegistrationWithEmail.getEmail().equals(currentEmail)) {
	                throw new Exception("Email already exists: " + newEmail);
	            }

	            Registration existingRegistration = registerRepo.findByEmail(currentEmail);

	            
	            if (existingRegistration == null) {
	                throw new EntityNotFoundException("Registration not found for email: " + currentEmail);
	            }

	            
	            existingRegistration.setEmail(newEmail); 

	          
	            return registerRepo.save(existingRegistration);
	        } catch (DataAccessException ex) {
	            
	            throw new Exception("Error updating registration: " + ex.getMessage(), ex);
	        }
	    }
	public String generateOTP(int length) {
		String numbers="0123456789";
		
		StringBuilder otp=new StringBuilder(length);
		
		Random random=new Random();
		for(int i=0;i<=length;i++) {
		otp.append(numbers.charAt(random.nextInt(numbers.length())));
		}
		
		return otp.toString();
	}

	public boolean checkMail(String email) {
	    Optional<Registration> register = Optional.ofNullable(registerRepo.findByEmail(email));
	    
	    
	    if (register.isEmpty()) {
	        return false;
	    }
	    return true;
	}

//	@Transactional
//	//public void updateEmail(String oldEmail, String newEmail) {
//	    // Fetch the existing entity by its primary key (email)
//	    Registration registration = registerRepo.findById(oldEmail)
//	            .orElseThrow(() -> new EntityNotFoundException("Registration not found with email: " + oldEmail));
//
//	    registration.setEmail(newEmail);
//
//	    registerRepo.save(registration);
//
//	   
//	    List<String> a=new ArrayList<>(Arrays.asList("apple","banana","cherry","mango","apple"));
//        a.stream().forEach(s->System.out.println(s));
//    boolean s= a.stream().filter(s->s.equals("orange")).findAny().isPresent();
//        
//	}
	
}
