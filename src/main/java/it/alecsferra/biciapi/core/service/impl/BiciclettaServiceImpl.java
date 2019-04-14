package it.alecsferra.biciapi.core.service.impl;

import it.alecsferra.biciapi.core.model.entity.Bicicletta;
import it.alecsferra.biciapi.core.model.entity.Stazione;
import it.alecsferra.biciapi.core.repository.BicicletteRepository;
import it.alecsferra.biciapi.core.service.BicicletteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BiciclettaServiceImpl implements BicicletteService {

    @Autowired
    BicicletteRepository bicicletteRepository;

    @Override
    public boolean saveBicicletta(Bicicletta bici){

        bicicletteRepository.save(bici);

        return true;

    }


    @Override
    public Optional<Bicicletta> findById(Long id) {
        return bicicletteRepository.findById(id);
    }

    @Override
    public Optional<Bicicletta> findByTagrfid(String tagrfid) {
        return bicicletteRepository.findByTagrfid(tagrfid);
    }

    @Override
    public List<Bicicletta> findAllByStazioneCorrente(Stazione stazione) {
        return bicicletteRepository.findAllByStazioneCorrente(stazione);
    }
}
