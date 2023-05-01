package com.swn.jamu.repository;

import com.swn.jamu.model.DistributorStockHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributorStockHistoryRepository extends JpaRepository<DistributorStockHistory, Long> {

    Page<DistributorStockHistory> findByDistributorStockId(long distributorStockId, Pageable pageable);
}
