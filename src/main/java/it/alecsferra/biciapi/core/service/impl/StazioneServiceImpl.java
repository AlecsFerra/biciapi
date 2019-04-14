package it.alecsferra.biciapi.core.service.impl;

import it.alecsferra.biciapi.core.model.entity.Stazione;
import it.alecsferra.biciapi.core.repository.StazioniRepository;
import it.alecsferra.biciapi.core.service.StazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StazioneServiceImpl implements StazioniService {

    private final StazioniRepository stazioneRepository;

    @Autowired
    public StazioneServiceImpl(StazioniRepository stazioneRepository) {
        this.stazioneRepository = stazioneRepository;
    }

    @Override
    public Optional<Stazione> findById(Long id) {
        return stazioneRepository.findById(id);
    }
}
