package com.investinfo.capital.repository;

import com.investinfo.capital.model.SnapshotPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface SnapshotPositionRepository extends JpaRepository<SnapshotPosition, Long> {

    List<SnapshotPosition> findByDateSnapshot(LocalDate dateSnapshot);

    @Query("SELECT new map(s.ticker as key, s.amount as value) FROM SnapshotPosition s")
    List<Map<String, BigDecimal>> findTickerAndAmountMap();
}
