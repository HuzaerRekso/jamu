package com.swn.jamu.repository;

import com.swn.jamu.model.BranchProcurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchProcurementRepository extends JpaRepository<BranchProcurement, Long> {

    @Query("SELECT bp FROM BranchProcurement bp")
    Page<BranchProcurement> findPaginated(Pageable pageable);

    @Query("SELECT bp FROM BranchProcurement bp WHERE bp.branch.id = :branchId")
    Page<BranchProcurement> findPaginatedBranch(@Param("branchId") long branchId, Pageable pageable);
}
