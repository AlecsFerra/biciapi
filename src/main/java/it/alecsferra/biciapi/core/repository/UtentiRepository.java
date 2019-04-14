package it.alecsferra.biciapi.core.repository;

import it.alecsferra.biciapi.core.model.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtentiRepository extends JpaRepository<Utente, Long> {

    Optional<Utente> findById(Long id);

    Optional<Utente> findByUsername(String username);

}
