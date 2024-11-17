package com.investinfo.capital.repository;

import com.investinfo.capital.model.ImoexPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationTypeRepository extends JpaRepository<ImoexPosition, Integer> {
}
