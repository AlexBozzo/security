package com.univr.backend.mappers;

import com.univr.backend.db.entities.UserEntity;
import com.univr.backend.models.SignUpRequest;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

   public UserEntity map(SignUpRequest sign){

      UserEntity user = new UserEntity();

      user.setName(sign.getName());
      user.setSurname(sign.getSurname());
      user.setEmail(sign.getEmail());
      user.setPassword(sign.getPassword());

      user.setDateOfBirth(sign.getDateOfBirth());
      user.setPlaceOfBirth(sign.getPlaceOfBirth());
      user.setTaxCode(sign.getTaxCode());


      return user;
   }
}
