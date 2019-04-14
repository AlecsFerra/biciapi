package it.alecsferra.biciapi.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@NoArgsConstructor
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @NotBlank
    @Column
    private String ruolo;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 20)
    @Column(unique = true)
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 60, max = 60) // BCrypt Size
    @Column
    private String password;

    @Column(name = "id_card")
    private Long idCard;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 40)
    @Column
    private String nome;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 40)
    @Column
    private String cognome;

}
