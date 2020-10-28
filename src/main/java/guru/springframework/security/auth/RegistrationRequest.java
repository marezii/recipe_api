package guru.springframework.security.auth;

import guru.springframework.security.ApplicationUserRole;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private ApplicationUserRole role;

}
