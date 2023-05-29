package com.alex.passport.repository;

import com.alex.passport.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisponibilitaRepository extends JpaRepository<Availability, Integer> {
  // Alcuni metodi di query personalizzati, se necessario
}