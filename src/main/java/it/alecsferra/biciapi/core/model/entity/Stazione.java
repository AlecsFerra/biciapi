package it.alecsferra.biciapi.core.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "stazioni")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Stazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 40)
    @Column
    private String indirizzo;

    @NotNull
    @Column(name = "pos_latitudine")
    private float posLatitudine;

    @NotNull
    @Column(name = "pos_longitudine")
    private float posLongitudine;

    @NotNull
    @Column(name = "num_posti_totale")
    private long numPostiTotale;

}
