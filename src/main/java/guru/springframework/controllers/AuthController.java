package guru.springframework.controllers;

import guru.springframework.domain.ApplicationUser;
import guru.springframework.jwt.UsernameAndPasswordAuthenticationRequest;
import guru.springframework.security.auth.RegistrationRequest;
import guru.springframework.services.ApplicationUserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public ApplicationUser registerUser(@RequestBody @Valid RegistrationRequest registrationRequest){

        ApplicationUser user = new ApplicationUser();
        user.setPassword(registrationRequest.getPassword());
        user.setUsername(registrationRequest.getUsername());
        user.setRole(registrationRequest.getRole());
        user = applicationUserService.registerUser(user);
        return user;
    }

    @DeleteMapping("/deleteAccount")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteAccount(@RequestParam String username){
        applicationUserService.deleteUser(username);
    }

    @PostMapping("/auth")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDetails loginUser(@RequestBody @Valid UsernameAndPasswordAuthenticationRequest request){

        return applicationUserService.loadUserByUsername(request.getUsername());

    }
}
