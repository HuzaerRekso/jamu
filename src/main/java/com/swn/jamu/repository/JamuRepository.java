package com.swn.jamu.repository;

import com.swn.jamu.model.Jamu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JamuRepository extends JpaRepository<Jamu, Long> {

    Page<Jamu> findByActive(Boolean active, Pageable pageable);

    Page<Jamu> findByActiveAndNameContaining(Boolean active, String name, Pageable pageable);

    boolean existsByCodeAndActive(String code, Boolean active);

    List<Jamu> findByActive(boolean active);
}
