/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mudar.backend.Atores.repository;

import Mudar.backend.Atores.entity.SaldoTransportador;
import Mudar.backend.Atores.entity.Transportador;
import Mudar.backend.Emolumento.entity.Saldo;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Alvaro
 */
public interface SaldoTransportadorRepository extends JpaRepository<SaldoTransportador,UUID>{
     /**
     * Método que retorna a classe SaldoTransportador pela busca através do ID do saldo.
     * @param transportador
     * @return
     */
    SaldoTransportador findByIdtrans(Transportador transportador);
    
    /**
     * 
     * @param transportador
     * @return 
     */
    boolean existsByIdtrans(Transportador transportador);
    /**
     * Método que retorna a classe SaldoTransportador pela busca através do ID do saldo.
     * @param saldo
     * @return
     */
    SaldoTransportador findByIdSal(Saldo saldo);
}



































































































