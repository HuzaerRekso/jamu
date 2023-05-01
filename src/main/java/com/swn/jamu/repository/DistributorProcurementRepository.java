package com.swn.jamu.repository;

import com.swn.jamu.model.DistributorProcurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributorProcurementRepository extends JpaRepository<DistributorProcurement, Long> {

    @Query("SELECT dp FROM DistributorProcurement dp")
    Page<DistributorProcurement> findPaginated(Pageable pageable);
}
