package guru.springframework.jwt;


import guru.springframework.security.passwordValidator.ValidPassword;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UsernameAndPasswordAuthenticationRequest {

    private String username;

    @ValidPassword
    private String password; //Primer admin adminA1! maintainer maintainerM1! user userU1!!

}
