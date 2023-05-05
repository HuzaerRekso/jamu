package com.swn.jamu.repository;

import com.swn.jamu.model.DistributorProcurementDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributorProcurementDetailRepository extends JpaRepository<DistributorProcurementDetail, Long> {
}
