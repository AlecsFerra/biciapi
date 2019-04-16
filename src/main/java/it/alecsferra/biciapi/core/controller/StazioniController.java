package it.alecsferra.biciapi.core.controller;

import it.alecsferra.biciapi.core.model.dto.output.PrelevazioneDto;
import it.alecsferra.biciapi.core.model.entity.Bicicletta;
import it.alecsferra.biciapi.core.model.entity.Noleggio;
import it.alecsferra.biciapi.core.model.entity.Stazione;
import it.alecsferra.biciapi.core.model.entity.Utente;
import it.alecsferra.biciapi.core.service.BicicletteService;
import it.alecsferra.biciapi.core.service.NoleggioService;
import it.alecsferra.biciapi.core.service.StazioniService;
import it.alecsferra.biciapi.core.service.UtentiService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static it.alecsferra.biciapi.core.Utils.getCurrentUsername;

@RestController
public class StazioniController {

    private final ModelMapper modelMapper;
    private final StazioniService stazioniService;
    private final UtentiService utenteService;
    private final NoleggioService noleggioService;
    private final BicicletteService bicicletteService;

    @Autowired
    public StazioniController(StazioniService statzioniService,
                              UtentiService utenteService,
                              BicicletteService bicicletteService,
                              NoleggioService noleggioService) {
        this.stazioniService = statzioniService;
        this.utenteService = utenteService;
        this.bicicletteService = bicicletteService;
        this.noleggioService = noleggioService;
        this.modelMapper = new ModelMapper();
    }

    @GetMapping(value = "/stazioni")
    List<Stazione> getAll() {
        return stazioniService.findAll();
    }

    @GetMapping(value = "/stazioni/{idStazione}")
    ResponseEntity<Stazione> infoStazione(@PathVariable long idStazione) {

        Optional<Stazione> stazione = stazioniService.findById(idStazione);

        if(!stazione.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).eTag("Stazione con id " + idStazione + "non trovata!").build();

        return ResponseEntity.of(stazione);

    }

    @GetMapping(value = "/stazioni/{idStazione}/posti")
    ResponseEntity<Long> getPosti(@PathVariable long idStazione) {

        Optional<Stazione> stazione = stazioniService.findById(idStazione);

        if(!stazione.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).eTag("Stazione con id " + idStazione + "non trovata!").build();

        List<Bicicletta> bici = bicicletteService.findAllByStazioneCorrente(stazione.get());

        return ResponseEntity.ok(stazione.get().getNumPostiTotale() - bici.size());

    }

    @GetMapping(value = "/stazioni/{idStazione}/preleva")
    ResponseEntity<PrelevazioneDto> preleva(@PathVariable long idStazione) {

        PrelevazioneDto ret = new PrelevazioneDto();

        Optional<Stazione> stazione = stazioniService.findById(idStazione);

        if(!stazione.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).eTag("Stazione con id " + idStazione + "non trovata!").build();

        Utente user = utenteService.findByUsername(getCurrentUsername()).get();

        if(!user.getRuolo().equals("user"))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).eTag("Gli utenti" + user.getRuolo() + " non possono noleggiare!").build();

        List<Bicicletta> bici = bicicletteService.findAllByStazioneCorrente(stazione.get());

        System.out.println(bici);

        if(bici.size() < 1){
            ret.setStatus(false);
            return ResponseEntity.ok(ret);
        }

        Bicicletta taken = bici.remove(0);

        taken.setStazioneCorrente(null);

        bicicletteService.saveBicicletta(taken);

        Noleggio noleggio = new Noleggio();

        noleggio.setBicicletta(taken);
        noleggio.setUtente(user);
        noleggio.setStazionePrelievo(stazione.get());
        noleggio.setDataOraPrelievo(new Timestamp(new Date().getTime()));

        noleggioService.saveNoleggio(noleggio);

        ret.setBicicletta(taken);
        ret.setIdNoleggio(noleggio.getId().longValue());
        ret.setStatus(true);

        return ResponseEntity.ok(ret);

    }

    @GetMapping(value = "/stazioni/{idStazione}/consegna/{idNoleggio}")
    ResponseEntity<Boolean> consegna(@PathVariable long idStazione, @PathVariable long idNoleggio) {

        Optional<Stazione> stazione = stazioniService.findById(idStazione);

        if(!stazione.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).eTag("Stazione con id " + idStazione + "non trovata!").build();

        Optional<Noleggio> noleggio = noleggioService.findById(idNoleggio);

        if(!stazione.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).eTag("Noleggio con id " + idNoleggio + "non trovato!").build();

        Utente user = utenteService.findByUsername(getCurrentUsername()).get();

        if(!user.equals(noleggio.get().getUtente()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).eTag("Puoi restituire solo le bici prese da te").build();

        if(stazione.get().getNumPostiTotale()
                - bicicletteService.findAllByStazioneCorrente(stazione.get()).size()
                <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).eTag("Stazione piena").build();

        Bicicletta rest = noleggio.get().getBicicletta();
        rest.setStazioneCorrente(stazione.get());
        bicicletteService.saveBicicletta(rest);

        noleggio.get().setStazioneConsegna(stazione.get());
        noleggio.get().setDataOraConsegna(new Timestamp(new Date().getTime()));
        noleggioService.saveNoleggio(noleggio.get());

        return ResponseEntity.ok(true);

    }

}
