package guru.springframework.services;

import guru.springframework.domain.ApplicationUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface ApplicationUserService extends UserDetailsService {
    ApplicationUser registerUser(ApplicationUser user);

}
