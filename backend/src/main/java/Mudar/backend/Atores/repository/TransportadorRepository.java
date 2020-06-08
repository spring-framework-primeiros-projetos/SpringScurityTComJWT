/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mudar.backend.Atores.repository;


import Mudar.backend.Atores.entity.Transportador;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alvaro
 */
@Repository
public interface TransportadorRepository extends JpaRepository<Transportador, UUID> {
    
    @Type(type="pg-uuid")
    @Query(value = "SELECT t FROM Transportador t JOIN Usuario u ON u.id = t.id where t.carteira = :carteira ")
        public List<Transportador> existsByCarteira(String carteira);
    
}
