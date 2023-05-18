package com.univr.backend.services;

import com.univr.backend.db.entities.UserEntity;
import com.univr.backend.db.repositories.UserRepository;
import com.univr.backend.mappers.UserEntityMapper;
import com.univr.backend.models.LoginRequest;
import com.univr.backend.models.SignUpRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class AuthService {
   @Autowired
   private UserEntityMapper userEntityMapper;
   @Autowired
   private UserRepository userRepository;

   public void signUp(SignUpRequest signUpRequest){
      UserEntity user = userEntityMapper.map(signUpRequest);
      userRepository.save(user);
   }

   public void login(LoginRequest loginRequest){
      Optional<UserEntity> user = userRepository.findByEmail(loginRequest.getUsername());
      if (user.isPresent()){
         log.info("Utente presente");
      }else {
         log.info("Utente NON presente");
      }
   }
}


