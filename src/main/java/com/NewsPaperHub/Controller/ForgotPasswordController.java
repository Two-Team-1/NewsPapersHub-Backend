package com.NewsPaperHub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.NewsPaperHub.Entity.Registration;
import com.NewsPaperHub.Service.ForgotPasswordService;
import com.NewsPaperHub.Service.RegisterService;

@RestController
@RequestMapping("/api/auth")
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private RegisterService registrationService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> sendOtp(@RequestParam String email) {
        if (!registrationService.checkMail(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not registered.");
        }
        forgotPasswordService.sendOtp(email);
        return ResponseEntity.ok("OTP sent to your email.");
    }

    @PostMapping("/reset-password")	
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword) {
        if (forgotPasswordService.verifyOtp(email, otp)) {
            Registration user = registrationService.findByEmail(email);
            user.setPassword(newPassword); // Hash this password using a PasswordEncoder if security is critical.
            registrationService.addregister(user);
            return ResponseEntity.ok("Password updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP or OTP expired.");
    }
}
