/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mudar.backend.Atores.repository;

import Mudar.backend.Atores.entity.ContaTransportador;
import Mudar.backend.Atores.entity.Transportador;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alvaro
 */
public interface ContaTransportadorRepository extends JpaRepository<ContaTransportador, UUID>{
   
    /**
    * Método que retorna a classe ContaTransportador pela busca através do ID do Transportador.
    * @param id
    * @return
    */
    ContaTransportador findByTransportador(Transportador transportador);
    
    boolean existsByTransportador(Transportador transportador);
}
