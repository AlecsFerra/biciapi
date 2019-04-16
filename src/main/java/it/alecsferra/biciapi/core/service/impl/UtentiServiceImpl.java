package it.alecsferra.biciapi.core.service.impl;

import it.alecsferra.biciapi.core.model.dto.input.LoginDto;
import it.alecsferra.biciapi.core.model.dto.output.DettagliUtente;
import it.alecsferra.biciapi.core.model.dto.output.LoginResultDto;
import it.alecsferra.biciapi.core.model.entity.Utente;
import it.alecsferra.biciapi.core.repository.UtentiRepository;
import it.alecsferra.biciapi.core.service.UtentiService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static it.alecsferra.biciapi.core.jwt.JwtUtils.getToken;

@Service
public class UtentiServiceImpl implements UtentiService {

    private final UtentiRepository utentiRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Value("${jwt.signing-key}")
    private String signingKey;

    @Value("${jwt.expire-time}")
    private long expireTime;

    @Autowired
    public UtentiServiceImpl(UtentiRepository utentiRepository,
                           PasswordEncoder passwordEncoder){
        this.utentiRepository = utentiRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean saveUser(Utente user) {

        Optional<Utente> old = utentiRepository.findByUsername(user.getUsername());

        if(old.isPresent())
            return false;

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        utentiRepository.save(user);

        return true;

    }

    @Override
    public Optional<Utente> findByUsername(String username) {
        return utentiRepository.findByUsername(username);
    }

    @Override
    public Optional<Utente> findById(Long id) {
        return utentiRepository.findById(id);
    }

    @Override
    public LoginResultDto generateToken(LoginDto loginUser) {

        LoginResultDto result = new LoginResultDto();

        String username = loginUser.getUsername();

        result.setUsername(username);

        Optional<Utente> user = findByUsername(username);

        if (!user.isPresent()) return result;

        boolean passwordCheck =
                passwordEncoder.matches(loginUser.getPassword(), user.get().getPassword());

        if (passwordCheck){

            long expDate = System.currentTimeMillis() + expireTime*1000;
            String token = getToken(username, expDate, signingKey.getBytes());
            result.setToken(token);
            result.setExpireDate(expDate);

        }

        return result;
    }

    @Override
    public List<DettagliUtente> findAll() {
        return utentiRepository.findAll()
                               .stream()
                               .map(x -> modelMapper.map(x, DettagliUtente.class))
                               .filter(x -> !x.getRuolo().equals("admin"))
                               .collect(Collectors.toList());
    }
}
