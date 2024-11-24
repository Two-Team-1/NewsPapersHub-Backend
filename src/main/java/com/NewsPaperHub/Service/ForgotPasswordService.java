package com.NewsPaperHub.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class ForgotPasswordService {
	 @Value("${spring.mail.username}")
	    private String sender;

	    @Autowired
	    private JavaMailSender javaMailSender;

	    private Map<String, String> otpStorage = new HashMap<>();
	    private Map<String, Long> otpTimestamps = new HashMap<>();

	    // Send OTP Email
	    public void sendOtp(String email) {
	        String otp = generateOtp();
	        otpStorage.put(email, otp);
	        otpTimestamps.put(email, System.currentTimeMillis());

	        // Build email content
	        String emailContent = "<p>Dear User,</p>" +
	                              "<p>Your OTP for resetting your password is: <b>" + otp + "</b></p>" +
	                              "<p>This OTP is valid for 5 minutes.</p>" +
	                              "<p>Regards,<br>Newspapers Hub Team</p>";

	        try {
	            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
	            helper.setText(emailContent, true);
	            helper.setTo(email);
	            helper.setSubject("OTP for Password Reset");
	            helper.setFrom(sender);
	            javaMailSender.send(mimeMessage);
	        } catch (Exception e) {
	            throw new RuntimeException("Error sending OTP email: " + e.getMessage());
	        }
	    }

	    // Verify OTP
	    public boolean verifyOtp(String email, String otp) {
	        if (otpStorage.containsKey(email) && otpStorage.get(email).equals(otp)) {
	            long timestamp = otpTimestamps.get(email);
	            if ((System.currentTimeMillis() - timestamp) <= 5 * 60 * 1000) { // 5 minutes
	                otpStorage.remove(email);
	                otpTimestamps.remove(email);
	                return true;
	            }
	        }
	        return false;
	    }

	    private String generateOtp() {
	        return String.valueOf((int) (Math.random() * 9000) + 1000); // 4-digit OTP
	    }

}
