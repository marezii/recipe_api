package guru.springframework.security.auth;

import guru.springframework.security.ApplicationUserRole;
import guru.springframework.security.passwordValidator.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    @ValidPassword
    private String password;

    private ApplicationUserRole role;

}
