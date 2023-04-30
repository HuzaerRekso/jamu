package com.swn.jamu.repository;

import com.swn.jamu.model.BranchStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchStockRepository extends JpaRepository<BranchStock, Long> {

    BranchStock findByBranchIdAndAndBaseJamuId(long branchId, long baseJamuId);

    Page<BranchStock> findByBranchIdAndAndBaseJamuNameContaining(long branchId, String baseJamuName, Pageable pageable);

    Page<BranchStock> findByBranchId(long branchId, Pageable pageable);
}
