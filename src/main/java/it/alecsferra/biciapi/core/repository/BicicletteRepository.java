package it.alecsferra.biciapi.core.repository;

import it.alecsferra.biciapi.core.model.entity.Bicicletta;
import it.alecsferra.biciapi.core.model.entity.Stazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BicicletteRepository extends JpaRepository<Bicicletta, Long> {

    Optional<Bicicletta> findById(Long id);

    Optional<Bicicletta> findByTagrfid(String tagrfid);

    List<Bicicletta> findAllByStazioneCorrente(Stazione stazione);

}
