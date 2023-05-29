package com.alex.passport.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Availability {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne
  private OperationRequest request;
  @ManyToOne
  private Headquarter headquarter;
  private LocalDate localDate;
  private LocalTime localTime;
  private int availability;
}