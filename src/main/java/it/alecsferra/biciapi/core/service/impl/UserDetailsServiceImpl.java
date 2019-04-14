package it.alecsferra.biciapi.core.service.impl;

import it.alecsferra.biciapi.core.model.entity.Utente;
import it.alecsferra.biciapi.core.repository.UtentiRepository;
import it.alecsferra.biciapi.core.service.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UtentiRepository utentiRepository;

    @Autowired
    public UserDetailsServiceImpl(UtentiRepository utentiRepository){
        this.utentiRepository = utentiRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

         Optional<Utente> user = utentiRepository.findByUsername(s);

        if(!user.isPresent())
            throw new UsernameNotFoundException(s + " user not found.");

        return new org.springframework.security.core.userdetails.User(
                user.get().getUsername(),
                user.get().getPassword(),
                Collections.emptyList()
        );
    }
}
