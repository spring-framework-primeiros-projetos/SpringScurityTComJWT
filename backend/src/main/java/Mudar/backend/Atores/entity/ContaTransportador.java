package Mudar.backend.Atores.entity;



import Mudar.backend.Emolumento.entity.Conta;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * A classe  ContaTransportador é o identificador do relacionamento entre transportador e as contas que possui.
 * @author Alvaro
 */
@Entity
@Table(name="CONTA_TRANSPORTADOR")
public class ContaTransportador implements Serializable {
    
    /**
     * A variável ID alocará a identificação sistêmica do Usuário e seus descendentes.
     */
    @Id
    @Column(name="ID_CONTA_TRANSPORTADOR",length = 40, nullable = false,insertable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;
    
    /**
     * I identificador idcon é o identificador de classe conta do cliente.
     */
    @JoinColumn(name="CONTA",unique = true, nullable = false,insertable = true, updatable = true )
    @OneToOne()
    private Conta conta;
    
    /**
     * O atributo idtrans é o identificador do saldo do transportador para verificar as possíveis contas para depósito do transportador.
     */
    @JoinColumn(name="TRANSPORTADOR",unique = true, nullable = false,insertable = true)
    @OneToOne()
    private Transportador transportador;

    public ContaTransportador() {
    }
    
    public ContaTransportador(UUID id, Conta conta, Transportador transportador) {
        this.id = id;
        this.conta = conta;
        this.transportador = transportador;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Transportador getTransportador() {
        return transportador;
    }

    public void setTransportador(Transportador transportador) {
        this.transportador = transportador;
    }

    @Override
    public String toString() {
        return "ContaTransportador{" + "id=" + id + ", conta=" + conta + ", transportador=" + transportador + '}';
    }
    
    
}
