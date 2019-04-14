package it.alecsferra.biciapi.core.repository;

import it.alecsferra.biciapi.core.model.entity.Stazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StazioniRepository extends JpaRepository<Stazione, Long> {

    Optional<Stazione> findById(Long id);

}
