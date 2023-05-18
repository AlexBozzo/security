package com.univr.backend.db.repositories;

import com.univr.backend.db.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
   public Optional<UserEntity> findByEmail(String email);
}
