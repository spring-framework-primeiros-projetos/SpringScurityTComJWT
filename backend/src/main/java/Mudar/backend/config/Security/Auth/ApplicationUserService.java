package Mudar.backend.config.Security.Auth;

import Mudar.backend.Atores.entity.Usuario;
import Mudar.backend.Atores.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService implements UserDetailsService {

    @Autowired
    private UsuarioRepository bdUsuario;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = bdUsuario.findByMail(mail);
        if(usuario == null) throw new UsernameNotFoundException(String.format("Username %s not found", mail));
        return usuario;
    }
}
