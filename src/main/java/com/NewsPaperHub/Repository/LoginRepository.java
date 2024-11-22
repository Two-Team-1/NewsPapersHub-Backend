package com.NewsPaperHub.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.NewsPaperHub.Entity.Loginpage;


@Repository
public interface LoginRepository extends JpaRepository<Loginpage,String> {

	

	Loginpage findByUserName(String email);

	Optional<Loginpage> findByuserName(String username);



	





}
