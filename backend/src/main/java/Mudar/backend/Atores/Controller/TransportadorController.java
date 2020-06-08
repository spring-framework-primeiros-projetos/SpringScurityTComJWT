/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mudar.backend.Atores.Controller;

import Mudar.backend.Atores.entity.Transportador;
import Mudar.backend.Atores.repository.TransportadorRepository;
import Mudar.backend.Json.Response.JsonResponse;
import Mudar.backend.Role.ApplicationUserRole;
import Mudar.backend.Validator.CpfCnpjUtils;
import Mudar.backend.Validator.DateValidator;
import Mudar.backend.Validator.IDMaker;
import Mudar.backend.Validator.TextValidator;
import Mudar.backend.Validator.ValidarCNH;
import Mudar.backend.config.Security.Jwt.JwtConfig;
import Mudar.backend.config.Security.Jwt.TokenId;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.annotation.security.PermitAll;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Alvaro
 */
@RestController
@RequestMapping("/transportador")
@Secured("ROLE_TRANSPORTADOR")
public class TransportadorController {
    @Autowired
    private TransportadorRepository bdtransportador;
    
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
    private final Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>(ApplicationUserRole.TRANSPORTADOR.getGrantedAuthorities());
    

    public TokenId Tid= new TokenId(secretKey, jwtConfig);
    
    @GetMapping(value = "/teste")
    public ResponseEntity<String> solicitante(){
        return ResponseEntity.ok("CRUD de transportador possível");
    }

    /**
     * Método para cadastro no banco de Solicitante.
     * 
     * @param transportador
     * @return
     */
    @PostMapping("")
    @PermitAll
    public ResponseEntity<Object> cadastra( @Valid @RequestBody Transportador transportador) {
        transportador.setId(id.GeraID());
        transportador.setNome(TextValidator.toTitledCase(transportador.getNome()));
        transportador.setMail(transportador.getMail().toLowerCase());
        transportador.setAuthorities(grantedAuthorities);
        transportador.setPassword(encoder.encode(transportador.getPassword()));
        transportador.setRole(ApplicationUserRole.TRANSPORTADOR);
        // remover após implantação de validação de acesso.
        transportador.setEnabled(true);
        transportador.setAccountNonExpired(true);
        transportador.setAccountNonLocked(true);
        transportador.setTokenExpired(false);
        transportador.setCredentialsNonExpired(true);
        // remover após implantação de validação de acesso.
        if(!CpfCnpjUtils.isValid(transportador.getCpf())){
            json = new JsonResponse("CPF inválido");
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(json);
        }
        if(!ValidarCNH.validaCNH(transportador.getCarteira())){
            json = new JsonResponse("CNH inválida");
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(json);
        }
        if(!bdtransportador.existsByCarteira(transportador.getCarteira()).isEmpty()){
            json = new JsonResponse("Carteira de motorista já registrada!");
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(json);
        }
        if(!transportador.mais18(transportador.getNascimento())){
            json = new JsonResponse("Você não é maior de 18 anos");
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(json);
        }
        try {
        return ResponseEntity.status(HttpStatus.CREATED).body(bdtransportador.getOne(bdtransportador.save(transportador).getId()));
        } 
        catch (Exception e ) {
            json = new JsonResponse("Dados já cadastrados\n" + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
        }
    }
    
    /**
     * Método para busca no banco um Solicitante.
     * @param request
     * @return 
     */
    @GetMapping("")
    @PreAuthorize("hasAuthority('transportador:read')")
    public ResponseEntity<Object> Buscar(HttpServletRequest request) {
        UUID TokenID =  Tid.GetId(request.getHeader(jwtConfig.getAuthorizationHeader()), jwtConfig.getTokenPrefix(), secretKey);
        if (bdtransportador.existsById(TokenID)) {
            return ResponseEntity.ok(bdtransportador.getOne(TokenID));
        }else{
            json = new JsonResponse("Transportador não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json);
        }
    }
    
    /**
     * Método que atualiza no banco os dados do Solicitante.
     * @param transportador
     * @param request
     * @return
     */
    @PutMapping("")
    @PreAuthorize("hasAuthority('transportador:update')")
        public ResponseEntity<Object> Atualizar(@Valid @RequestBody Transportador transportador,HttpServletRequest request) {
        
        UUID TokenID =  Tid.GetId(request.getHeader(jwtConfig.getAuthorizationHeader()), jwtConfig.getTokenPrefix(), secretKey);
        if(!TokenID.equals(transportador.getId())){
            json = new JsonResponse("Dados não condizem com assinatura");
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(json);
        }
        if (bdtransportador.existsById(transportador.getId())) {
            System.out.println("Encontrado para alteração.");
            transportador.setNome(TextValidator.toTitledCase(transportador.getNome()));
            transportador.setMail(transportador.getMail().toLowerCase());
            transportador.setNome(TextValidator.toTitledCase(transportador.getNome()));
            grantedAuthorities.addAll(ApplicationUserRole.TRANSPORTADOR.getGrantedAuthorities());
            transportador.setAuthorities(grantedAuthorities);
            transportador.setPassword(encoder.encode(transportador.getPassword()));
            transportador.setMail(transportador.getMail().toLowerCase());
            if(!CpfCnpjUtils.isValid(transportador.getCpf())){
                json = new JsonResponse("CPF inválido");
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(json);
            }
            if(!ValidarCNH.validaCNH(transportador.getCarteira())){
                json = new JsonResponse("CNH inválida");
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(json);
            }
            if(!transportador.mais18(transportador.getNascimento())){
                json = new JsonResponse("Você não é maior de 18 anos");
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(json);
            }
            Transportador existente = bdtransportador.getOne(transportador.getId());
            BeanUtils.copyProperties(transportador, existente, transportador.getId().toString());
            existente = bdtransportador.save(existente);
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
    @PreAuthorize("hasAuthority('transportador:delete')")
    public ResponseEntity<Object> Deletar(HttpServletRequest request) {
        UUID TokenID =  Tid.GetId(request.getHeader(jwtConfig.getAuthorizationHeader()), jwtConfig.getTokenPrefix(), secretKey);
        if (bdtransportador.existsById(TokenID)) {
            json = new JsonResponse("Encontrado para Deleção.");
            try{
                bdtransportador.deleteById(TokenID);
                return ResponseEntity.noContent().build();
            }catch (Exception e ) {
                json = new JsonResponse("problema na exclusão do Transportador.\n" + e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
            }
        }else{
            json = new JsonResponse("Transportador não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json);
        }
    }
}
 	 	
