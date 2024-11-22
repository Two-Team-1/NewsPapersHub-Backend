package com.NewsPaperHub.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NewsPaperHub.Entity.Registration;


@Repository
public interface RegistrationReposotory extends JpaRepository<Registration, String>{

	Registration findByEmail(String email);
//boolean findByemail(String email);

//boolean findByemail(String email);


	//List<Registration> findBycustomerId(String string);


//List<Registration> findByCustomerId(String string);



}
