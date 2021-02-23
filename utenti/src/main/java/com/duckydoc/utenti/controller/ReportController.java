package com.duckydoc.utenti.controller;

import com.duckydoc.utenti.model.Report;
import com.duckydoc.utenti.model.Utente;
import com.duckydoc.utenti.repo.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ReportController {
    @Autowired
    ReportRepository repository;

    @GetMapping("/reports")
    public List<Report> getAllReporgts(){
        List<Report> reports = new ArrayList<>();
        repository.findAll().forEach(reports::add);
        return reports;
    }

    @GetMapping("/reports/{reportId}")
    public Report getReportById(@PathVariable long reportId){
        return repository.findById(reportId);
    }

    @PostMapping("reports/create")
    public void postreport(@RequestBody Report report){
        repository.save(report);
    }

}
