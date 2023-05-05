package com.swn.jamu.repository;

import com.swn.jamu.model.BranchProcurementDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchProcurementDetailRepository extends JpaRepository<BranchProcurementDetail, Long> {
}
