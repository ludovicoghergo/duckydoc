package com.duckydoc.utenti.repo;

import com.duckydoc.utenti.model.Report;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<Report, Long> {
    Report findById(long Id);
}
