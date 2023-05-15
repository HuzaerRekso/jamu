package com.swn.jamu.repository;

import com.swn.jamu.model.BranchProcurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchProcurementRepository extends JpaRepository<BranchProcurement, Long> {

    @Query("SELECT bp FROM BranchProcurement bp")
    Page<BranchProcurement> findPaginated(Pageable pageable);

    @Query("SELECT bp FROM BranchProcurement bp WHERE bp.branch.id = :branchId")
    Page<BranchProcurement> findPaginatedBranch(@Param("branchId") long branchId, Pageable pageable);

    @Query(value = "SELECT * FROM branch_procurement WHERE status = :status AND branch_id = :branchId ORDER BY request_date ASC", nativeQuery = true)
    List<BranchProcurement> findByStatusAndBranch(@Param("status") String status, @Param("branchId") long branchId);
}
