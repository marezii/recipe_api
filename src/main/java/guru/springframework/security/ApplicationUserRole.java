package guru.springframework.security;


import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static guru.springframework.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {

    ADMIN(Sets.newHashSet()),
    USER(Sets.newHashSet(RECIPE_READ, UOM_READ, INGREDIENT_READ)),
    MAINTAINER(Sets.newHashSet(RECIPE_READ, RECIPE_WRITE, RECIPE_UPDATE,
            UOM_READ, UOM_WRITE, UOM_UPDATE,
            INGREDIENT_READ, INGREDIENT_WRITE, INGREDIENT_UPDATE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
