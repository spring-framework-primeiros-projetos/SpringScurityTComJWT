package Mudar.backend.Atores.entity;



import Mudar.backend.Role.ApplicationUserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Calendar;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Classe abstrata para criação de usuários na ferramenta
 */
@Entity
@Table(name="USUARIO")
@Inheritance(strategy=InheritanceType.JOINED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@DiscriminatorColumn(name = "TIPO_USUARIO", length = 1, discriminatorType = DiscriminatorType.STRING)
@SuppressWarnings("PersistenceUnitPresent")
public abstract class Usuario implements  UserDetails {

    /**
     * A variável ID alocará a identificação sistêmica do Usuário e seus descendentes.
     */
    @Id
    @Column(name="ID_USUARIO",length = 40, unique = true, nullable = false,insertable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;
    
    /**
     * A variável nome alocará o nome do Usuário e seus descendentes.
     */
    @Length( max = 255, message = "O nome deve conter o máximo de 255 caracteres.")
    @NotNull(message = "Nome não pode estar vazio.")
    @Column(name="NOME", nullable = false)
    private String nome;

    /**
     * A variável CPF alocará o Cadastro de Pessoa Física (CPF) do Usuário e seus descendentes
     */
    @NotNull(message = "CPF não pode estar vazio.")
    @Column(name="CPF", length = 11, unique = true, nullable = false)
    private String cpf;

    /**
     * A variável telefone alocará o telefone para contato do Usuário e seus descendentes.
     */
    @NotNull(message = "Telefone não pode estar vazio.")
    @Column(name="TELEFONE", length = 12, nullable = true)
    private long telefone;

    /**
     * A variável mail alocará o e-mail do Usuário e seus descendentes.
     */
    @Length(max = 100, message = "O nome deve conter o máximo de 100 caracteres.")
    @NotNull(message = "E-mail não pode estar vazio.")
    @Column(name="E_MAIL", nullable = false,length = 100)
    @Email(message = "E-mail inválido")
    private String mail;

    /**
     * A variável senha alocará a senha do Usuário e seus descendentes, a senha deve ser criptografada de alguma forma de ponta a ponta no sistema.
     */
    @Length(max = 255, message = "A senha deve conter no máximo 255 caracteres.")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "Senha não pode estar vazio.")
    @Column(name="PASSWORD", nullable = false)
    private String password;

    /**
     * A variável RG alocará ao valor do Registro Geral (RG) do Usuário e seus descendentes.
     */
    @Length(max = 255, message = "O RG deve conter no máximo 255 caracteres.")
    @NotNull(message = "RG não pode estar vazio.")
    @Column(name="RG", nullable = false)
    private String rg;

    /**
     * A variável nascimento alocará a data de nascimento do Usuário e seus descendentes.
     * 
     */
    @NotNull(message = "Data de nascimento não pode estar vazio.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.000+0000")
    @Column(name="NASCIMENTO", nullable = false)
    @Temporal(TemporalType.DATE)
    @Past(message = "Data não é Válida para o nascimento")
    private Calendar nascimento;
    
    /**
     * Identificador para verificar se usuário esta habilitado para utilizar o sistema.
     */
    private  boolean Enabled;
    
    /**
     * Identificador para verificar se o token esta expirado.
     */
    private boolean tokenExpired;
    
    /**
     * Identificador para verificar se conta esta expirada.
     */
    private  boolean AccountNonExpired;
    
    /**
     * Identificador para verificar se conta esta bloqueada.
     */
    private  boolean AccountNonLocked;
    
    /**
     * Identificador para verificar se as credenciais estão expiradas.
     */
    private  boolean CredentialsNonExpired;
    

    /**
     * 
     */
    @Enumerated(EnumType.STRING)
    private ApplicationUserRole role;
    
    @ElementCollection(targetClass=GrantedAuthority.class,fetch=FetchType.EAGER)
     @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "ID_USUARIO", referencedColumnName = "ID_USUARIO"))
    //@Enumerated(EnumType.STRING)
    @Fetch(FetchMode.SELECT)
    private Set<? extends GrantedAuthority> authorities;
    
    /**
     * Construtor vazio não utilizar.   
     */
    public Usuario() {
    }

    /**
     * O construtor da classe do Usuário e poderá ser usada como super em seus descendentes.
     * @param id
     * @param nome
     * @param cpf
     * @param nascimento
     * @param telefone
     * @param password
     * @param mail
     * @param rg
     */
    public Usuario(UUID id, String nome, String cpf, long telefone, String mail, String password, String rg, Calendar nascimento) {
    this.id =  id;
    this.nome = nome;
    this.cpf = cpf;
    this.telefone = telefone;
    this.mail = mail;
    this.password = password;
    this.rg = rg;
    this.nascimento = nascimento;
    }
    /**
     * Verifica se data de nascimento é maior de 18 anos.
     * @param nascimento
     * @return 
     */
    public boolean mais18(Calendar nascimento){
        Calendar hoje = Calendar.getInstance();
        if((hoje.get(Calendar.YEAR) - nascimento.get(Calendar.YEAR)) >= 19){
            return true;
        }else if((hoje.get(Calendar.YEAR) - nascimento.get(Calendar.YEAR)) == 18){
                if((hoje.get(Calendar.MONTH)>= nascimento.get(Calendar.MONTH))){
                    if(hoje.get(Calendar.DAY_OF_MONTH) >= nascimento.get(Calendar.DAY_OF_MONTH)){
                        return true;
                    }
                }
        }
        return false;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public long getTelefone() {
        return telefone;
    }

    public void setTelefone(long telefone) {
        this.telefone = telefone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Calendar getNascimento() {
        return nascimento;
    }

    public void setNascimento(Calendar nascimento) {
        this.nascimento = nascimento;
    }

    @Override
    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean Enabled) {
        this.Enabled = Enabled;
    }

    public boolean isTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(boolean tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    @Override
    public boolean isAccountNonExpired() {
        return AccountNonExpired;
    }

    public void setAccountNonExpired(boolean AccountNonExpired) {
        this.AccountNonExpired = AccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return AccountNonLocked;
    }

    public void setAccountNonLocked(boolean AccountNonLocked) {
        this.AccountNonLocked = AccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return CredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean CredentialsNonExpired) {
        this.CredentialsNonExpired = CredentialsNonExpired;
    }

    public ApplicationUserRole getRole() {
        return role;
    }

    public void setRole(ApplicationUserRole role) {
        this.role = role;
    }
    
    @Override
    public String getUsername() {
        return mail;
    }

    public void setAuthorities(Set<? extends GrantedAuthority> grantedAuthorities) {
        this.authorities= grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    
}
