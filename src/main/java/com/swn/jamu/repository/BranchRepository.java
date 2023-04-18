package com.swn.jamu.repository;

import com.swn.jamu.model.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    Page<Branch> findByNameContainingAndActive(String name, Boolean active, Pageable pageable);

    Page<Branch> findByActive(Boolean active, Pageable pageable);

    boolean existsByCodeAndActive(String code, Boolean active);

    List<Branch> findByActive(Boolean active);
}
