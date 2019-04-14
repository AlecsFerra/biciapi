package it.alecsferra.biciapi.core.model.dto.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResultDto {

    private String username;

    private String token;

    private Long expireDate;

}
