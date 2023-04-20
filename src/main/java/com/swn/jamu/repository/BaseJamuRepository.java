package com.swn.jamu.repository;

import com.swn.jamu.model.BaseJamu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseJamuRepository extends JpaRepository<BaseJamu, Long> {

    Page<BaseJamu> findByActive(Boolean active, Pageable pageable);

    Page<BaseJamu> findByActiveAndNameContaining(Boolean active, String name, Pageable pageable);

    boolean existsByCodeAndActive(String code, Boolean active);

    List<BaseJamu> findByActive(Boolean active);
}
