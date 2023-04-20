package com.swn.jamu.repository;

import com.swn.jamu.model.Dose;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoseRepository extends CrudRepository<Dose, Long> {

    List<Dose> findByJamuId(long jamuId);
}
