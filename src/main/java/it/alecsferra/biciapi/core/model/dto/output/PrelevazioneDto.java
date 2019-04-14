package it.alecsferra.biciapi.core.model.dto.output;

import it.alecsferra.biciapi.core.model.entity.Bicicletta;
import it.alecsferra.biciapi.core.model.entity.Noleggio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PrelevazioneDto {

    private boolean status;

    private Bicicletta bicicletta;

    private long idNoleggio;

}
