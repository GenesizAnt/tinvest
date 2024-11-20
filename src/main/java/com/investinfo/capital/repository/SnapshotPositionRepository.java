package com.investinfo.capital.repository;

import com.investinfo.capital.model.SnapshotPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SnapshotPositionRepository extends JpaRepository<SnapshotPosition, Long> {

    List<SnapshotPosition> findByDateSnapshot(LocalDate dateSnapshot);
}
