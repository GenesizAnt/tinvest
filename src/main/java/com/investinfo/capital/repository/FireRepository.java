package com.investinfo.capital.repository;

import com.investinfo.capital.model.Fire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FireRepository extends JpaRepository<Fire, Long> {
    Optional<Fire> getFiresById(Integer id);
}
