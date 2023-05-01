package com.swn.jamu.repository;

import com.swn.jamu.model.DistributorStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributorStockRepository extends JpaRepository<DistributorStock, Long> {

    DistributorStock findByBaseJamuId(long baseJamuId);

    Page<DistributorStock> findByBaseJamuNameContaining(String baseJamuName, Pageable pageable);
}
