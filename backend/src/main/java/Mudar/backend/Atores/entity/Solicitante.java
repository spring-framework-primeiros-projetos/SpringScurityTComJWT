package Mudar.backend.Atores.entity;

import java.util.Calendar;
import java.util.UUID;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * A Classe Solicitante representa o ator dentro da ferramenta com os dados básicos para solicitar o serviço ao sistema.
 * @author Alvaro
 */
@Entity
@Table(name = "SOLICITANTE")
@DiscriminatorValue("S")
@SuppressWarnings("PersistenceUnitPresent")
public class Solicitante extends Usuario {
    
    private static final String DISCRIMINATOR_VALUE = "S";

    public Solicitante() {
    }

    


    
    /**
     * O construtor para iniciar variável completa do sistema.
     * 
     * @param id
     * @param nome
     * @param cpf
     * @param telefone
     * @param mail
     * @param senha
     * @param rg
     * @param nascimento 
     */
    public Solicitante(UUID id, String nome, String cpf, long telefone, String mail, String senha, String rg, Calendar nascimento) {
        super(id, nome, cpf, telefone, mail, senha, rg, nascimento);
    }

	public void PagarServico(float valor) {

	}

    
}
