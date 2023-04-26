package com.swn.jamu.repository;

import com.swn.jamu.model.BranchSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchSaleRepository extends JpaRepository<BranchSale, Long> {
}
