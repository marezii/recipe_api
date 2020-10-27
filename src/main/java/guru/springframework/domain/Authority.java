package guru.springframework.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import guru.springframework.security.ApplicationUserPermission;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ApplicationUserPermission authority;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private ApplicationUser user;

    @Override
    public String getAuthority() {
        return null;
    }
}
