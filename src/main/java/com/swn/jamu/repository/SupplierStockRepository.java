package com.swn.jamu.repository;

import com.swn.jamu.model.SupplierStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierStockRepository extends JpaRepository<SupplierStock, Long> {

    SupplierStock findByBaseJamuId(long baseJamuId);

    Page<SupplierStock> findByBaseJamuNameContaining(String baseJamuName, Pageable pageable);

    List<SupplierStock> findByIdIn(List<Long> ids);
}
