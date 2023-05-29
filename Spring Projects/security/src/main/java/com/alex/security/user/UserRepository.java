package com.alex.security.user;
// CLASSE N°3

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Interfaccia responsabile per la comunicazione fra user e database
@Repository             // Indica a spring di leggere questa classe come una repossitory
public interface UserRepository extends JpaRepository<User, Integer> {

    // JpaRepository possiede già dei metodi, qui ne possiamo definire altri a piacimento
    // come ad esempio questo metodo di ricerca, non definito in Jpa
    Optional<User> findByEmail(String email);

}
