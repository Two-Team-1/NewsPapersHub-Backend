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
@RequestMapping("/register")
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

    @PostMapping("/sendEmail/emailVerification/{emailRequest}")
    public ResponseEntity<String> emailVerification(@PathVariable String emailRequest) {
        String postUrl = "https://api.zeptomail.in/v1.1/email";
        StringBuffer sb = new StringBuffer();
        String otp = registerservice.generateOTP(5);
        try {
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Zoho-enczapikey PHtE6r0EFLjr3jMsp0QAt/+wE8TyN40tr+hmKFMVsIgUXqMFTk0Bqdl6wDPiqU8jXPJHR/ObzN5ttLOe5+ONdGrtZG1NXmqyqK3sx/VYSPOZsbq6x00etFUdcE3aUIbvetFq0ifQvdbcNA==");

            JSONObject requestBody = new JSONObject();
            JSONObject from = new JSONObject();
            String email = "RS Insurance";
            from.put("address", email);
            requestBody.put("from", from);

            JSONObject to = new JSONObject();
            JSONObject emailAddress = new JSONObject();
            emailAddress.put("address", emailRequest);
            to.put("email_address", emailAddress);
            requestBody.put("to", new JSONObject[]{to});

            requestBody.put("subject", "Email Verification for Rs Insurence");
            String greeting = "thanks & regards";
            String ofcName = "RS Insurance pvt ltd.";
            String address1 = "Madhapur, Hyderabad,";
            String address2 = "Telangana, India. 500081";

            requestBody.put("htmlbody", "Dear Custumer,Otp to verify the email in Rs Insurance pvt ltd. Here is you 6 digits one time password: <h3> " + otp + " " + "<h5>" + greeting + "<br/>" + ofcName + "<br/>" + address1 + "<br/>" + address2 + "");
            OutputStream os = conn.getOutputStream();
            os.write(requestBody.toString().getBytes());
            os.flush();

            BufferedReader br;
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                if (inputStream != null) {
                    br = new BufferedReader(new InputStreamReader(inputStream));
                    String output;
                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }
                    br.close();
                }
            } else {
                InputStream errorStream = conn.getErrorStream();
                if (errorStream != null) {
                    br = new BufferedReader(new InputStreamReader(errorStream));
                    String output;
                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }
                    br.close();
                }
            }
            conn.disconnect();

            return ResponseEntity.ok(otp);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(otp);
        }
    }

    @PostMapping("/sendEmail/emailUpdation/{emailRequest}")
    public ResponseEntity<String> emailUpdation(@PathVariable String emailRequest) {
        // Similar to emailVerification
        return emailVerification(emailRequest);
    }

    @GetMapping("/sendOtp")
    public ResponseEntity<String> sendOtp(@RequestParam String mobileno, @RequestParam String otp) {
        String url = "https://login4.spearuc.com/MOBILE_APPS_API/sms_api.php?type=smsquicksend&user=qtnextotp&pass=987654&sender=QTTINF"
                + "&t_id=1707170494921610008&to_mobileno=" + mobileno
                + "&sms_text=" + "Dear customer, use this OTP " + otp + " to signup into your Quality Thought Next account. This OTP will be valid for the next 15 mins";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(response);
    }
}
