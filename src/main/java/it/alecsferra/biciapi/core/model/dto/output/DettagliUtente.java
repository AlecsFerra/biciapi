package it.alecsferra.biciapi.core.model.dto.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DettagliUtente {

    private String username;

    private String nome;

    private String cognome;

    private Long idCard;

    private String ruolo;

}
