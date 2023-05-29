package com.alex.passport.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class OperationRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String request_type;
  @ManyToOne
  private Citizien citizien;
  private LocalDate request_date;
}