package Mudar.backend.Atores.entity;


import Mudar.backend.Emolumento.entity.Saldo;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * A Classe Saldotransportador é o detentor da relação do transportador com o seu saldo de serviços acumulados para pagamento do transportador.
 * @author Alvaro
 */ 
@Entity
@Table(name="SALDO_TRANSPORTADOR")
@SuppressWarnings("PersistenceUnitPresent")
public class SaldoTransportador implements Serializable{
    
    @Id
    @Column(name="ID_SALDO_TRANSPORTADOR",unique = true, nullable = true, updatable = false)
    private UUID id;
    
    /**
     * O atributo idtrans é o identificador o transportador para relacionar os destinatário do pagamento que deve ser feito.
     */
    @JoinColumn(name="ID_TRANSPORTADOR",unique = true, nullable = false,updatable = false )
    @OneToOne()
    private Transportador idtrans;
    
    /**
     * O atributo idSal é o identificador do saldo do transportador para verificar o valor do transportador para pagar.
     */
    @JoinColumn(name="ID_SALDO", unique = true, nullable = false, updatable = false )
    @OneToOne()
    private Saldo idSal;

    /**
     * 
     */
    public SaldoTransportador() {
    }

    /**
     * 
     * @param id
     * @param idtrans
     * @param idSal 
     */
    public SaldoTransportador(UUID id, Transportador idtrans, Saldo idSal) {
        this.id = id;
        this.idtrans = idtrans;
        this.idSal = idSal;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Transportador getIdtrans() {
        return idtrans;
    }

    public void setIdtrans(Transportador idtrans) {
        this.idtrans = idtrans;
    }

    public Saldo getIdSal() {
        return idSal;
    }

    public void setIdSal(Saldo idSal) {
        this.idSal = idSal;
    }

    @Override
    public String toString() {
        return "SaldoTransportador{" + "id= " + id + ", idtrans= " + idtrans + ", idSal= " + idSal + '}';
    }
    
    
}


