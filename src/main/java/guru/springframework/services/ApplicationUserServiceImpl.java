package guru.springframework.services;

import guru.springframework.domain.ApplicationUser;
import guru.springframework.repositories.ApplicationUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ApplicationUserServiceImpl implements ApplicationUserService {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserRepository applicationUserRepository;

    public ApplicationUserServiceImpl(PasswordEncoder passwordEncoder, ApplicationUserRepository applicationUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    @Transactional
    public ApplicationUser registerUser(ApplicationUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return applicationUserRepository.save(user);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = applicationUserRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        }
        user.setGrantedAuthorities(user.getRole().getGrantedAuthorities());
        return user;
    }
}
