package com.swn.jamu.repository;

import com.swn.jamu.model.Jamu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JamuRepository extends JpaRepository<Jamu, Long> {

    Page<Jamu> findByActive(Boolean active, Pageable pageable);

    Page<Jamu> findByActiveAndNameContaining(Boolean active, String name, Pageable pageable);

    boolean existsByCodeAndActive(String code, Boolean active);

    List<Jamu> findByActive(boolean active);

    @Query(value = "SELECT * FROM jamu j ORDER BY j.id DESC LIMIT 1", nativeQuery = true)
    Jamu findLatest();

    @Query(value = "SELECT j.* FROM jamu j JOIN dose d on j.id = d.jamu_id WHERE d.base_jamu_id IN (:ids)", nativeQuery = true)
    List<Jamu> findAvailableJamu(List<Long> ids);
}
