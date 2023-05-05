package com.swn.jamu.repository;

import com.swn.jamu.model.BranchStockHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchStockHistoryRepository extends JpaRepository<BranchStockHistory, Long> {

    Page<BranchStockHistory> findByBranchStockId(long branchStockId, Pageable pageable);
}
