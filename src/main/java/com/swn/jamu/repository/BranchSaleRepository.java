package com.swn.jamu.repository;

import com.swn.jamu.model.BranchSale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BranchSaleRepository extends JpaRepository<BranchSale, Long> {

    Page<BranchSale> findByBranchId(long branchId, Pageable pageable);

    List<BranchSale> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);
}
