/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mudar.backend.config.Security.Jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.util.UUID;
import javax.crypto.SecretKey;

/**
 *
 * @author Alvaro
 */
public class TokenId {
    
    
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public TokenId(SecretKey secretKey, JwtConfig jwtConfig) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }
    
    
    
    public UUID GetId(String authorizationHeader, String TokenPrefix,SecretKey secretKey ){
        

        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(TokenPrefix)) {
            return null;
        }
       
        String token = authorizationHeader.replace(TokenPrefix, "");


            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            String username = body.getSubject();
            String id = body.get("ID", String.class);
            return UUID.fromString(body.get("ID", String.class));
            
    }
}
