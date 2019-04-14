package it.alecsferra.biciapi.core.service;

import it.alecsferra.biciapi.core.model.entity.Bicicletta;
import it.alecsferra.biciapi.core.model.entity.Stazione;

import java.util.List;
import java.util.Optional;

public interface BicicletteService {

    boolean saveBicicletta(Bicicletta b);

    Optional<Bicicletta> findById(Long id);

    Optional<Bicicletta> findByTagrfid(String tagrfid);

    List<Bicicletta> findAllByStazioneCorrente(Stazione stazione);

}
