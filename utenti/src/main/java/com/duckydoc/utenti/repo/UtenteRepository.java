package com.duckydoc.utenti.repo;

import com.duckydoc.utenti.model.Utente;
import org.springframework.data.repository.CrudRepository;

public interface UtenteRepository extends CrudRepository<Utente, Long> {
    Utente findById(long idUtente);

    Utente findByEmail(String email);
}
