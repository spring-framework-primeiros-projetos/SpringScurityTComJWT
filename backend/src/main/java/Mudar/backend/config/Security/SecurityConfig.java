/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mudar.backend.config.Security;

import Mudar.backend.Atores.repository.UsuarioRepository;
import Mudar.backend.config.Security.Auth.ApplicationUserService;
import Mudar.backend.config.Security.Jwt.JwtConfig;
import Mudar.backend.config.Security.Jwt.JwtTokenVerifier;
import Mudar.backend.config.Security.Jwt.JwtUsernameAndPasswordAuthenticationFilter;
import Mudar.backend.config.Security.Jwt.TokenId;
import com.google.common.collect.Lists;
import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 *
 * @author Alvaro
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@CrossOrigin
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ApplicationUserService applicationUserService;
    
    @Autowired
    private SecretKey secretKey;
    
    @Autowired
    private JwtConfig jwtConfig;
    
    @Autowired
    private UsuarioRepository bdusuario;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
                .csrf().disable()
                .cors()
//                    .configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                    .configurationSource(corsConfigurationSource())
//                    .disable()
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey, bdusuario))
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig),JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/").permitAll()
                .antMatchers(HttpMethod.POST, "/transportador").permitAll()
                .antMatchers(HttpMethod.POST, "/solicitante").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest()
                .authenticated()
                ;
    }
    
    @Bean
	CorsConfigurationSource corsConfigurationSource() {
            final CorsConfiguration configuration = new CorsConfiguration();
            configuration.addAllowedOrigin("*");
            configuration.addAllowedHeader("*");
            configuration.addAllowedMethod("*");
            configuration.setAllowCredentials(true);
            configuration.setExposedHeaders(Lists.newArrayList("role","Set-Cookie0", 
                    "Authorization","Content-Type","Date","Cache-Control","Connection"));
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }
        
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
       
}
