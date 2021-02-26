package com.DuckyDoc.Report.repo;

import com.DuckyDoc.Report.model.Utente;
import org.springframework.data.repository.CrudRepository;

public interface UtenteRepository extends CrudRepository<Utente, Long> {
    Utente findById(long idUtente);
}
