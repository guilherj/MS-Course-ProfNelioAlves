package com.devsuperior.hroauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 * Essa classe serve para configurar a autenticação,
 * que ocorre quando a nossa aplicação vai receber as credenciais do usuário e vai chamar o usuário
 * pelo username do banco de dados do ms de user, copara as senhas e ver se bateu, e se bater 
 * vai autenticar o usuário e devolver um token.
 * 
 */
@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	/* Foi necessário fazer a sobrecarga desse método só para podermos
	 * injetar ele como bean no projeto. 
	 * 
	 * E foi necessário colocar esse anotação para que outro componente do projeto tenha acesso a esse bean
	 * que nesse caso foi a classe AuthorizationServerConfig
	 * 
	 */	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	
	
	
}
