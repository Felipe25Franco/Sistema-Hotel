package com.example.SCHapi.config;

import com.example.SCHapi.security.JwtAuthFilter;
import com.example.SCHapi.security.JwtService;
import com.example.SCHapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(usuarioService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().disable()
            .csrf().disable()
            .authorizeRequests()
            //ESTADIA
                .antMatchers("/api/v1/produtoSolicitados/**")
                    .permitAll()
                .antMatchers("/api/v1/quartoHospedagens/**")
                    .permitAll()
                .antMatchers("/api/v1/relacaoHorarioServicos/**")
                    .permitAll()
                .antMatchers("/api/v1/servicoSolicitados/**")
                    .permitAll()
                .antMatchers("/api/v1/tipoQuartoReservas/**")
                    .permitAll()
                .antMatchers("/api/v1/avaliacaoHospedagens/**")
                    .permitAll()
                .antMatchers("/api/v1/avaliacaoQuartos/**")
                    .permitAll()
                .antMatchers( "/api/v1/hospedagens/**")
                    .permitAll()
                .antMatchers( "/api/v1/reservas/**")
                    .permitAll()
                .antMatchers( "/api/v1/statusHospedagens/**")
                    .permitAll()
                .antMatchers( "/api/v1/statusReservas/**")
                    .permitAll()
            //PESSOA
                .antMatchers("/api/v1/cargos/**")
                    .permitAll()
                .antMatchers("/api/v1/clientes/**")
                    .permitAll()
                .antMatchers("/api/v1/enderecos/**")
                    .permitAll()
                .antMatchers("/api/v1/funcionarios/**")
                    .permitAll()
                .antMatchers("/api/v1/hoteis/**")
                    .permitAll()
                .antMatchers("/api/v1/paises/**")
                    .permitAll()
                .antMatchers("/api/v1/ufs/**")
                    .permitAll()
            //PRODUTO
                .antMatchers("/api/v1/produtos/**")
                    .permitAll()
                .antMatchers("/api/v1/tipoProdutos/**")
                    .permitAll()
            //QUARTO
                .antMatchers("/api/v1/comodidadeTipoQuartos/**")
                    .permitAll()
                .antMatchers("/api/v1/tipoCamaTipoQuartos/**")
                    .permitAll()
                .antMatchers("/api/v1/comodidades/**")
                    .permitAll()
                .antMatchers("/api/v1/quartos/**")
                    .permitAll()
                .antMatchers("/api/v1/statusQuartos/**")
                    .permitAll()
                .antMatchers("/api/v1/tipoCamas/**")
                    .permitAll()
                .antMatchers("/api/v1/tipoComodidades/**")
                    .permitAll()
                .antMatchers("/api/v1/tipoQuartos/**")
                    .permitAll()
            //SERVICO
                .antMatchers("/api/v1/horarioServicos/**")
                    .permitAll()
                .antMatchers("/api/v1/servicos/**")
                    .permitAll()
                .antMatchers("/api/v1/statusServicos/**")
                    .permitAll()
                .antMatchers("/api/v1/tipoServicos/**")
                    .permitAll()
                .antMatchers("/api/v1/usuarios/**")
                    .permitAll()
                
              

                    //.antMatchers("/api/v1/vagas/**")
                    //.hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}