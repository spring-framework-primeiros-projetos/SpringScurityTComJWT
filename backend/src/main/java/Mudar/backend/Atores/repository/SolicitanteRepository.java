/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mudar.backend.Atores.repository;


import Mudar.backend.Atores.entity.Solicitante;
import Mudar.backend.Validator.IDMaker;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alvaro
 */
@Repository
public interface SolicitanteRepository extends JpaRepository<Solicitante, UUID>{

    public boolean existsById(IDMaker id);
    
}
