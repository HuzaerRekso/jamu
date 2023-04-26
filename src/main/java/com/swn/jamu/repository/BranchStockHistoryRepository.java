package com.swn.jamu.repository;

import com.swn.jamu.model.BranchStockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchStockHistoryRepository extends JpaRepository<BranchStockHistory, Long> {
}
