package com.alex.passport.repository;

import com.alex.passport.entity.Headquarter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SedeRepository extends JpaRepository<Headquarter, Integer> {
  // Alcuni metodi di query personalizzati, se necessario
}