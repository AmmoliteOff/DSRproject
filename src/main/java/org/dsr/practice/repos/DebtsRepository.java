package org.dsr.practice.repos;

import org.dsr.practice.models.Debt;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DebtsRepository extends CrudRepository<Debt,Long> {
    List<Debt> findByUserId(Long id);
}