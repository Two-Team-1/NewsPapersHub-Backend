package com.NewsPaperHub.Controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.NewsPaperHub.Entity.Registration;
import com.NewsPaperHub.Service.RegisterService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/register")
public class RegisterController {

    @Autowired
    RegisterService registerservice;

    @PostMapping("/addregister")
    private ResponseEntity<String> addregister(@RequestBody Registration register) {

        if (registerservice.checkMail(register.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Registration already done. Please login.");
        }

        Registration savedRegister = registerservice.addregister(register);

        try {
            registerservice.sendWelcomeEmail(savedRegister);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration successful, but failed to send the welcome email. Please contact support.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful. Please login.");
    }


    @GetMapping("/getAll")
    private ResponseEntity<List<Registration>> getAll() {
        return ResponseEntity.ok(registerservice.getAll());
    }

    @GetMapping("/getById/{email}")
    private ResponseEntity<Registration> getById(@PathVariable String email) {
        return ResponseEntity.ok(registerservice.getById(email));
    }

    @PostMapping("/CheckMail/{email}")
    private ResponseEntity<Boolean> checkMail(@PathVariable String email) {
        return ResponseEntity.ok(registerservice.checkMail(email));
    }

    @PutMapping("/user/update/{email}")
    private ResponseEntity<Registration> update(@RequestBody Registration register, @PathVariable String email) {
        return ResponseEntity.ok(registerservice.update(register, email));
    }

}