package guru.springframework.controllers;

import guru.springframework.domain.ApplicationUser;
import guru.springframework.jwt.UsernameAndPasswordAuthenticationRequest;
import guru.springframework.security.auth.RegistrationRequest;
import guru.springframework.services.ApplicationUserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final ApplicationUserService applicationUserService;

//    private final AuthenticationManager authenticationManager;

    public AuthController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("permitAll()")
    public ApplicationUser registerUser(@RequestBody @Valid RegistrationRequest registrationRequest){

        ApplicationUser user = new ApplicationUser();
        user.setPassword(registrationRequest.getPassword());
        user.setUsername(registrationRequest.getUsername());
        user.setRole(registrationRequest.getRole());
        user = applicationUserService.registerUser(user);
        return user;
    }

    @PostMapping("/auth")
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("permitAll()")
    public UserDetails loginUser(@RequestBody @Valid UsernameAndPasswordAuthenticationRequest request){

        return applicationUserService.loadUserByUsername(request.getUsername());

    }
}
