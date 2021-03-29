package com.duckydoc.utenti.repo;

import com.duckydoc.utenti.model.Utente;
import org.springframework.data.repository.CrudRepository;

public interface UtenteRepository extends CrudRepository<Utente, Long> {

    Utente findByIdGoogle(String idGoogle);
    Utente findById(long id);
    Utente findByEmail(String email);
}
