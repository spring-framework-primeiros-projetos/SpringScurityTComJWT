package Mudar.backend.Atores.entity;


import Mudar.backend.Enumeradores.Carteira;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Calendar;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Future;
import javax.validation.constraints.Past;

/**
 * A Classe Transportador representa o ator dentro da ferramenta com os dados básicos para atender o serviço solicitado pelo sistema.
 * @author Alvaro
 */
@Entity
@Table(name="TRANSPORTADOR")
@DiscriminatorValue("T")
@PrimaryKeyJoinColumn(name="ID_USUARIO")
@SuppressWarnings("PersistenceUnitPresent")
public class Transportador extends Usuario{
    

    private static final String DISCRIMINATOR_VALUE = "T";
    /**
     * A variável carteira alocará o número carteira de motorista do cliente.
     */
    
    @Column(name="CARTEIRA",length = 12, unique = true)
    private String carteira;

    /**
     * A classe validade alocará a validade da carteira de motorista.
     */
    //@CompositeMember(value = "CARTEIRA")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.000+0000")
    @Future(message = "Data da Validade da carteira não é válida.")
    @Column(name="CARTEIRA_VALIDADE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar validade;

    /**
     * A variável emissão alocará a data de emissão da carteira.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.000+0000")
    @Past(message = "Data de Emissão da carteira não é válida.")
    @Column(name="CARTEIRA_EMISSAO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar emissao;

    public Transportador() {
    }

    /**
     * A variável tipo alocará o tipo de carteira do Transportador.
     * 
     */
    @Enumerated(EnumType.STRING)
    @Column(name="CARTEIRA_TIPO", length = 2, columnDefinition = "char(1)")
    private Carteira TipoCarteira;
    
    /**
     *
     * @param id
     */
    public void ServiçoDisponível(Transportador id) {

    }
    /**
     * 
     * @param carteira
     * @param validade
     * @param emissao
     * @param id
     * @param nome
     * @param cpf
     * @param telefone
     * @param mail
     * @param senha
     * @param rg
     * @param TipoCarteira
     * @param nascimento 
     */
    public Transportador(UUID id, String nome, String cpf, int telefone, String mail, String senha, String rg, Calendar nascimento,String carteira, Calendar validade, Calendar emissao, Carteira TipoCarteira) {
        super(id, nome, cpf, telefone, mail, senha, rg, nascimento);
        this.carteira = carteira;
        this.validade = validade;
        this.emissao = emissao;
        this.TipoCarteira = TipoCarteira;
    }

    public String getCarteira() {
        return carteira;
    }

    public void setCarteira(String carteira) {
        this.carteira = carteira;
    }

    public Calendar getValidade() {
        return validade;
    }

    public void setValidade(Calendar validade) {
        this.validade = validade;
    }

    public Calendar getEmissao() {
        return emissao;
    }

    public void setEmissao(Calendar emissao) {
        this.emissao = emissao;
    }

    public Carteira getTipoCarteira() {
        return TipoCarteira;
    }

    public void setTipo(Carteira TipoCarteira) {
        this.TipoCarteira = TipoCarteira;
    }

    public static String getDISCRIMINATOR_VALUE() {
        return DISCRIMINATOR_VALUE;
    }
    
    
}
