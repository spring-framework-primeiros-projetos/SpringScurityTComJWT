package Mudar.backend.Role;

import static Mudar.backend.Role.ApplicationUserPermission.*;
import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;


public enum ApplicationUserRole {
    TRANSPORTADOR(Sets.newHashSet(TRANSPORTADOR_READ, TRANSPORTADOR_WRITE,TRANSPORTADOR_UPDATE,TRANSPORTADOR_DELETE)),
    SOLICITANTE(Sets.newHashSet(SOLICITANTE_READ, SOLICITANTE_WRITE,SOLICITANTE_UPDATE,SOLICITANTE_DELETE )),
    ADMINISTRADOR(Sets.newHashSet(SOLICITANTE_READ, SOLICITANTE_WRITE,SOLICITANTE_UPDATE,SOLICITANTE_DELETE , 
                                    TRANSPORTADOR_READ, TRANSPORTADOR_WRITE,TRANSPORTADOR_UPDATE,TRANSPORTADOR_DELETE))
    ;
    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
       Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
