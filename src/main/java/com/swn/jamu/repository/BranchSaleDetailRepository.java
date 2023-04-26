package com.swn.jamu.repository;

import com.swn.jamu.model.BranchSaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchSaleDetailRepository extends JpaRepository<BranchSaleDetail, Long> {
}
