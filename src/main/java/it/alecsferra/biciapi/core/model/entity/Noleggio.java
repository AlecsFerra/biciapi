package it.alecsferra.biciapi.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "noleggi")
@Getter
@Setter
@NoArgsConstructor
public class Noleggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_bicicletta", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Bicicletta bicicletta;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_stazione_prelievo", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Stazione stazionePrelievo;

    @NotNull
    @Column(name = "data_ora_prelievo")
    private Timestamp dataOraPrelievo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_utente", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Utente utente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stazione_consegna")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Stazione stazioneConsegna;

    @Column(name = "data_ora_consegna")
    private Timestamp dataOraConsegna;

}
