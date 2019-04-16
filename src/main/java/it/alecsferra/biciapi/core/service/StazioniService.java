package it.alecsferra.biciapi.core.service;

import it.alecsferra.biciapi.core.model.entity.Stazione;

import java.util.List;
import java.util.Optional;

public interface StazioniService {

    Optional<Stazione> findById(Long id);

    List<Stazione> findAll();

}
