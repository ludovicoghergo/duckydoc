package com.DuckyDoc.Report.Controller;

import com.DuckyDoc.Report.model.Report;
import com.DuckyDoc.Report.model.Utente;
import com.DuckyDoc.Report.repo.ReportRepository;
import com.DuckyDoc.Report.repo.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("")
public class ReportController {
    @Autowired
    ReportRepository repository;

    @Autowired
    UtenteRepository utenteRepository;

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

    @PostMapping("reports/{id_user}/create")
    public Report postReport(@RequestBody Report report, @PathVariable int id_user) {
        Utente u = utenteRepository.findById(id_user);
        report.setUser(u);
        //System.out.println(u.getName());
        return repository.save(report);
    }

}
