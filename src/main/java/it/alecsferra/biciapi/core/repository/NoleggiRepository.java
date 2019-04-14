package it.alecsferra.biciapi.core.repository;

import it.alecsferra.biciapi.core.model.entity.Noleggio;
import it.alecsferra.biciapi.core.model.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoleggiRepository extends JpaRepository<Noleggio, Long> {

    Optional<Noleggio> findById(Long id);

    List<Noleggio> findAllByDataOraConsegnaIsNull();

    List<Noleggio> findAllByUtente(Utente utente);
}
