package com.DuckyDoc.Report.Controller;

import com.DuckyDoc.Report.model.Report;
import com.DuckyDoc.Report.repo.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ReportController {
    @Autowired
    ReportRepository repository;

    @GetMapping("/reports")
    public List<Report> getAllReporgts() {
        List<Report> reports = new ArrayList<>();
        repository.findAll().forEach(reports::add);
        return reports;
    }

    @GetMapping("/reports/{reportId}")
    public Report getReportById(@PathVariable long reportId) {
        return repository.findById(reportId);
    }

    @GetMapping("/reports/{document_id}")
    public List<Report> getReportByDocument(@PathVariable long document_id) {
        return repository.findByDocumentId(document_id);
    }

    @PostMapping("reports/create")
    public Report postreport(@RequestBody Report report) {
        return repository.save(report);
    }

}
