package com.univr.backend.models;


import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class SignUpRequest {
   private String name;
   private String surname;
   private String email;
   private String password;

   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
   private LocalDate dateOfBirth;
   private String placeOfBirth;
   private String taxCode;
}
