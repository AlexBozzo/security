package com.univr.backend.db.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class UserEntity {
   private String name;
   private String surname;
   private String email;
   private String password;
   private LocalDate dateOfBirth;
   private String placeOfBirth;
   @Id
   private String taxCode;

}
