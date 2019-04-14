package it.alecsferra.biciapi.core.service;

import it.alecsferra.biciapi.core.model.dto.input.LoginDto;
import it.alecsferra.biciapi.core.model.dto.output.LoginResultDto;
import it.alecsferra.biciapi.core.model.entity.Utente;

import java.util.Optional;


public interface UtentiService {

    boolean saveUser(Utente user);

    Optional<Utente> findByUsername(String username);

    Optional<Utente> findById(Long id);

    LoginResultDto generateToken(LoginDto loginUser);


}
