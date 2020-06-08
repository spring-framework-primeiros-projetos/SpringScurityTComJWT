/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mudar.backend.Atores.Controller;


import Mudar.backend.Atores.entity.Solicitante;
import Mudar.backend.Atores.repository.SolicitanteRepository;
import Mudar.backend.Json.Response.JsonResponse;
import Mudar.backend.Role.ApplicationUserRole;
import Mudar.backend.Validator.CpfCnpjUtils;
import Mudar.backend.Validator.DateValidator;
import Mudar.backend.Validator.IDMaker;
import Mudar.backend.Validator.TextValidator;
import Mudar.backend.config.Security.Jwt.JwtConfig;
import Mudar.backend.config.Security.Jwt.TokenId;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.UUID;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alvaro
 */
@RestController
@RequestMapping("/solicitante")
@Secured("ROLE_SOLICITANTE")
public class SolicitanteController {
    @Autowired
    private SolicitanteRepository bdsolicitante;
    
    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private SecretKey secretKey;
    
    @Autowired
    private  JwtConfig jwtConfig;
    
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    private final IDMaker id = new IDMaker();
    private final DateValidator dv = new DateValidator();
    private final TextValidator txt = new TextValidator();
    private JsonResponse  json ;
    private final Set<SimpleGrantedAuthority> grantedAuthorities = ApplicationUserRole.SOLICITANTE.getGrantedAuthorities();
    

    public TokenId Tid= new TokenId(secretKey, jwtConfig);
    
    @GetMapping(value = "/teste")
    public ResponseEntity<String> solicitante(){
        return ResponseEntity.ok("CRUD de solicitante possível");
    }

    /**
     * Método para cadastro no banco de Solicitante.
     * 
     * @param solicitante
     * @return
     */
    @PostMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Object> cadastra(@Valid  @RequestBody Solicitante solicitante) {
        solicitante.setId(id.GeraID());
        solicitante.setNome(TextValidator.toTitledCase(solicitante.getNome()));
        solicitante.setMail(solicitante.getMail().toLowerCase());
        solicitante.setAuthorities(grantedAuthorities);
        solicitante.setRole(ApplicationUserRole.SOLICITANTE);
        solicitante.setPassword(encoder.encode(solicitante.getPassword()));
        // remover após implantação de validação de acesso.
        solicitante.setEnabled(true);
        solicitante.setAccountNonExpired(true);
        solicitante.setAccountNonLocked(true);
        solicitante.setTokenExpired(false);
        solicitante.setCredentialsNonExpired(true);
        //remover após implantação de validação de acesso.
        if(!CpfCnpjUtils.isValid(solicitante.getCpf())){
            json = new JsonResponse("CPF inválido");
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(json);
        }
        if(!solicitante.mais18(solicitante.getNascimento())){
            json = new JsonResponse("Você não é maior de 18 anos");
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(json);
        }
        try {
        return ResponseEntity.ok(bdsolicitante.getOne(bdsolicitante.save(solicitante).getId()));
        } 
        catch (Exception e ) {
            json = new JsonResponse("Dados já cadastrados \n" + e);
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(json);
        }
    }
    
    /**
     * Método para busca no banco um Solicitante.
     * @param request
     * @return 
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority('solicitante:read')")
    public ResponseEntity<Object> Buscar(HttpServletRequest request) {
           UUID TokenID =  Tid.GetId(request.getHeader(jwtConfig.getAuthorizationHeader()), jwtConfig.getTokenPrefix(), secretKey);
            if (bdsolicitante.existsById(TokenID)) {
                return ResponseEntity.status(HttpStatus.CREATED).body(bdsolicitante.getOne(TokenID));
            }else{
            json = new JsonResponse("Solicitante não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json);
        }
    }
    
    /**
     * Método que atualiza no banco os dados do Solicitante.
     * @param solicitante
     * @return
     */
    @PutMapping("")
    @PreAuthorize("hasAuthority('solicitante:update')")
    public ResponseEntity<Object> Atualizar(@Valid @RequestBody Solicitante solicitante,HttpServletRequest request) {
        UUID TokenID =  Tid.GetId(request.getHeader(jwtConfig.getAuthorizationHeader()), jwtConfig.getTokenPrefix(), secretKey);
        if(!TokenID.equals(solicitante.getId())){
            json = new JsonResponse("Dados não condizem com assinatura");
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(json);
        }
        if (bdsolicitante.existsById(solicitante.getId())) {
            solicitante.setNome(TextValidator.toTitledCase(solicitante.getNome()));
            solicitante.setMail(solicitante.getMail().toLowerCase());
            grantedAuthorities.addAll(ApplicationUserRole.TRANSPORTADOR.getGrantedAuthorities());
            solicitante.setAuthorities(grantedAuthorities);
            solicitante.setPassword(encoder.encode(solicitante.getPassword()));
            if(!CpfCnpjUtils.isValid(solicitante.getCpf())){
                json = new JsonResponse("CPF inválido");
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(json);
            }
            if(!solicitante.mais18(solicitante.getNascimento())){
                json = new JsonResponse("Você não é maior de 18 anos");
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(json);
            }
            Solicitante existente = bdsolicitante.getOne(solicitante.getId());
            BeanUtils.copyProperties(solicitante, existente, TokenID.toString());
            existente = bdsolicitante.save(existente);
            return ResponseEntity.ok(existente);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Método que deleta no banco os dados do Solicitante.
     * @param request
     * @return
     */
    @DeleteMapping("")
    @PreAuthorize("hasAuthority('solicitante:delete')")
    public ResponseEntity<Object> Deletar(HttpServletRequest request){
        UUID TokenID =  Tid.GetId(request.getHeader(jwtConfig.getAuthorizationHeader()), jwtConfig.getTokenPrefix(), secretKey);
        if (bdsolicitante.existsById(TokenID)) {
                System.out.println("Encontrado para Deleção.");
                try{
                    bdsolicitante.deleteById(TokenID);
                    return ResponseEntity.noContent().build();
                }catch (Exception e ) {
                    json = new JsonResponse("problema na exclusão do Solicitante.\n" + e);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
            }
        }else{
            json = new JsonResponse("Solicitante não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json);
        }
    }
}
