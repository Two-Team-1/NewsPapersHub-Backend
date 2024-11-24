package com.NewsPaperHub.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.NewsPaperHub.Entity.Loginpage;
import com.NewsPaperHub.Repository.RegistrationReposotory;
import com.NewsPaperHub.Service.LoginpageService;
import com.NewsPaperHub.Service.RegisterService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/Loginpage")
public class LoginpageController {

    @Autowired
    LoginpageService loginpageService;

    @Autowired
    RegisterService registerservice;

    @Autowired
    RegistrationReposotory registerRepo;

    public LoginpageController(LoginpageService loginpageService) {
        this.loginpageService = loginpageService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addlogin(@RequestBody Loginpage loginpage) {
        Optional<Loginpage> optionalUser = loginpageService.findByUserName(loginpage.getUserName());
        

        if (optionalUser.isPresent()) {
            Loginpage user = optionalUser.get();
            if (user.getpassword().equals(loginpage.getpassword())) {
             
                return ResponseEntity.ok("Login successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } else {
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Loginpage>> getAll(@RequestBody Loginpage loginpage) {
        List<Loginpage> allLogins = loginpageService.getAll(loginpage);
        return ResponseEntity.ok(allLogins);
    }

    @PutMapping("/update")
    public ResponseEntity<Loginpage> update(@RequestBody Loginpage loginpage) {
        Loginpage updatedLogin = loginpageService.update(loginpage);
        return ResponseEntity.ok(updatedLogin);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> delete(@RequestBody Loginpage loginpage) {
        loginpageService.delete(loginpage);
        return ResponseEntity.ok("Deleted successfully");
    }
}
