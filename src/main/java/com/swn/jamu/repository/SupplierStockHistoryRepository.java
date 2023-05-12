package com.swn.jamu.repository;

import com.swn.jamu.model.SupplierStockHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierStockHistoryRepository extends JpaRepository<SupplierStockHistory, Long> {

    Page<SupplierStockHistory> findBySupplierStockId(long supplierStockId, Pageable pageable);
}
