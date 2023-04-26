package com.swn.jamu.repository;

import com.swn.jamu.model.BranchStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchStockRepository extends JpaRepository<BranchStock, Long> {

    BranchStock findByBranchIdAndAndBaseJamuId(long branchId, long baseJamuId);
}
