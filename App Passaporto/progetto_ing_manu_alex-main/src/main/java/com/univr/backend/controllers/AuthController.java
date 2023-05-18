package com.univr.backend.controllers;

import com.univr.backend.models.LoginRequest;
import com.univr.backend.models.SignUpRequest;
import com.univr.backend.services.AuthService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Log4j2
@CrossOrigin
public class AuthController {

   @Autowired
   private AuthService authService;


   @PostMapping("/login")
   public String login(@RequestBody LoginRequest request){
      log.info("Login request received username: {} password: {}",request.getUsername(),request.getPassword());
      authService.login(request);

      return "Login successfully";
   }

   @PostMapping("/signup")
   public String signUp(@RequestBody SignUpRequest request){
      log.info("Registration request received name: {} surname: {} " +
              "dateOfBirth: {} placeOfBirth: {} taxCode: {}", request.getName(), request.getSurname(), request.getDateOfBirth(), request.getPlaceOfBirth(), request.getTaxCode());

      authService.signUp(request);
      return "Registration successfully";
   }



}
