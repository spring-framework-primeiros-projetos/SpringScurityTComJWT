/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mudar.backend.Atores.repository;

import Mudar.backend.Atores.entity.Usuario;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alvaro
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID>{

    public boolean existsByMail(String mail);

    public Usuario findByMail(String mail);
    
}
