package it.alecsferra.biciapi.core.service.impl;

import it.alecsferra.biciapi.core.model.entity.Noleggio;
import it.alecsferra.biciapi.core.model.entity.Utente;
import it.alecsferra.biciapi.core.repository.NoleggiRepository;
import it.alecsferra.biciapi.core.service.NoleggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoleggioServiceImpl implements NoleggioService {

    @Autowired
    NoleggiRepository noleggiRepository;

    @Override
    public boolean saveNoleggio(Noleggio n) {
        noleggiRepository.save(n);
        return true;
    }

    @Override
    public Optional<Noleggio> findById(Long n) {
        return noleggiRepository.findById(n);
    }

    @Override
    public List<Noleggio> findAllNonRestituiti() {
        return  noleggiRepository.findAllByDataOraConsegnaIsNull();
    }

    @Override
    public List<Noleggio> findAllByUtente(Utente utente) {
        return noleggiRepository.findAllByUtente(utente);
    }
}
