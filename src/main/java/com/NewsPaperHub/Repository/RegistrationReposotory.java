package com.NewsPaperHub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NewsPaperHub.Entity.Registration;


@Repository
public interface RegistrationReposotory extends JpaRepository<Registration, String>{

	Registration findByEmail(String email);


}
