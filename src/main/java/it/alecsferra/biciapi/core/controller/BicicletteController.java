package it.alecsferra.biciapi.core.controller;

import it.alecsferra.biciapi.core.Utils;
import it.alecsferra.biciapi.core.model.dto.output.BiciInUsoDto;
import it.alecsferra.biciapi.core.model.dto.output.DettagliUtente;
import it.alecsferra.biciapi.core.model.entity.Stazione;
import it.alecsferra.biciapi.core.model.entity.Utente;
import it.alecsferra.biciapi.core.service.NoleggioService;
import it.alecsferra.biciapi.core.service.UtentiService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static it.alecsferra.biciapi.core.Utils.getCurrentUsername;

@RestController
public class BicicletteController {

    private final ModelMapper modelMapper;
    private final NoleggioService noleggioService;
    private final UtentiService utentiService;

    @Autowired
    public BicicletteController(NoleggioService noleggioService, UtentiService  utentiService) {
        this.noleggioService = noleggioService;
        this.utentiService = utentiService;
        modelMapper = new ModelMapper();
    }

    @GetMapping(value = "/biciclette/inUso")
    ResponseEntity<List<BiciInUsoDto>> getAllBiciInUso() {

        Utente user = utentiService.findByUsername(getCurrentUsername()).get();

        if(!user.getRuolo().equals("admin"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).eTag("Admin only").build();


        return ResponseEntity.ok(noleggioService.findAllNonRestituiti()
                .stream()
                .map(x -> {

                    BiciInUsoDto dto = new BiciInUsoDto();
                    dto.setBicicletta(x.getBicicletta());
                    dto.setUtente(modelMapper.map(x.getUtente(), DettagliUtente.class));
                    dto.setStazionePrelevamento(x.getStazionePrelievo());

                    return dto;

                }).collect(Collectors.toList()));

    }

}
