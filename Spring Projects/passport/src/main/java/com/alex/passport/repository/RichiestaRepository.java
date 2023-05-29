package com.alex.passport.repository;

import com.alex.passport.entity.OperationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RichiestaRepository extends JpaRepository<OperationRequest, Integer> {
}