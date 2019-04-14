package it.alecsferra.biciapi.core.service;

import it.alecsferra.biciapi.core.model.entity.Noleggio;
import it.alecsferra.biciapi.core.model.entity.Utente;

import java.util.List;
import java.util.Optional;

public interface NoleggioService {

    boolean saveNoleggio(Noleggio n);

    Optional<Noleggio> findById(Long n);

    List<Noleggio> findAllNonRestituiti();

    List<Noleggio> findAllByUtente(Utente utente);

}
