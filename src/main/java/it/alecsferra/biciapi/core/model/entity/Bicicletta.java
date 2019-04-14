package it.alecsferra.biciapi.core.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.annotation.*;

@Entity
@Table(name = "biciclette")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bicicletta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 40)
    @Column(unique = true)
    @Size(min = 5, max = 5)
    //@RfidConstraint
    private String tagrfid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_stazione_corrente")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Stazione stazioneCorrente;

}

@Documented
@Constraint(validatedBy = RfidValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@interface RfidConstraint {
    String message() default "Invalid phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class RfidValidator implements
        ConstraintValidator<RfidConstraint, String> {

    @Override
    public void initialize(RfidConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.chars()
                .allMatch(x -> x >= 0x41 && x <= 0x5A);
    }


}