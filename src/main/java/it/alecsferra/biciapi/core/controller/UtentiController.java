package it.alecsferra.biciapi.core.controller;

import it.alecsferra.biciapi.core.Utils;
import it.alecsferra.biciapi.core.model.dto.input.LoginDto;
import it.alecsferra.biciapi.core.model.dto.input.RegisterUserDto;
import it.alecsferra.biciapi.core.model.dto.output.DettagliUtente;
import it.alecsferra.biciapi.core.model.dto.output.LoginResultDto;
import it.alecsferra.biciapi.core.model.dto.output.SimpleResult;
import it.alecsferra.biciapi.core.model.entity.Noleggio;
import it.alecsferra.biciapi.core.model.entity.Utente;
import it.alecsferra.biciapi.core.service.NoleggioService;
import it.alecsferra.biciapi.core.service.UtentiService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static it.alecsferra.biciapi.core.Utils.*;

@RestController(value = "users")
public class UtentiController {

    private final UtentiService utentiService;
    private final NoleggioService noleggioService;
    private final ModelMapper modelMapper;

    @Autowired
    public UtentiController(UtentiService utentiService, NoleggioService noleggioService) {
        this.utentiService = utentiService;
        this.noleggioService = noleggioService;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping("/register")
    public SimpleResult register(@RequestBody @Validated RegisterUserDto userDto, BindingResult bindingResult) {

        SimpleResult result = new SimpleResult();

        if (bindingResult.hasErrors())
            return result;

        Utente user = modelMapper.map(userDto, Utente.class);

        user.setIdCard(new Random().nextLong());
        user.setRuolo("user");

        boolean success = utentiService.saveUser(user);

        if (!success)
            result.setMessage("Username already exists.");

        result.setSuccess(success);

        return result;
    }


    @PostMapping("/login")
    public LoginResultDto login(@RequestBody @Validated LoginDto loginUser, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){

            LoginResultDto result = new LoginResultDto();
            result.setUsername(loginUser.getUsername());
            return result;

        }

        return utentiService.generateToken(loginUser);
    }

    @GetMapping("users/me")
    public DettagliUtente me(){

        String username = getCurrentUsername();

        Utente me = utentiService.findByUsername(username).get();

        return modelMapper.map(me, DettagliUtente.class);

    }

    @GetMapping("users/me/noleggi")
    public List<Noleggio> mineNoleggi(){

        String username = getCurrentUsername();

        Utente me = utentiService.findByUsername(username).get();

        List<Noleggio> noleggi = noleggioService.findAllByUtente(me);

        noleggi.forEach(x -> x.setUtente(null));

        return noleggi;

    }

    @GetMapping("users/{idUtente}/noleggi")
    public ResponseEntity<List<Noleggio>> userNoleggi(@PathVariable long idUtente){

        String username = getCurrentUsername();

        Utente me = utentiService.findByUsername(username).get();

        if(!me.getRuolo().equals("admin"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).eTag("Admin only").build();

        Optional<Utente> user = utentiService.findById(idUtente);

        if(!user.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).eTag("user not found").build();

        List<Noleggio> noleggi = noleggioService.findAllByUtente(user.get());

        noleggi.forEach(x -> x.setUtente(null));

        return ResponseEntity.ok(noleggi);

    }

}
