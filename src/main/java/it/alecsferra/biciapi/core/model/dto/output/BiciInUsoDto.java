package it.alecsferra.biciapi.core.model.dto.output;

import it.alecsferra.biciapi.core.model.entity.Bicicletta;
import it.alecsferra.biciapi.core.model.entity.Stazione;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BiciInUsoDto {

    private Bicicletta bicicletta;

    private DettagliUtente utente;

    private Stazione stazionePrelevamento;

}
