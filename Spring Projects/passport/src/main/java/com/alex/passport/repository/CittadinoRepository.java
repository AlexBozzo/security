package com.alex.passport.repository;

import com.alex.passport.entity.Citizien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CittadinoRepository extends JpaRepository<Citizien, Integer> {
}
