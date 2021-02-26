package com.DuckyDoc.Report.repo;

import com.DuckyDoc.Report.model.Report;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportRepository extends CrudRepository<Report, Long> {
    Report findById(long Id);
    List<Report> findByDocumentId(long document_id);
}
