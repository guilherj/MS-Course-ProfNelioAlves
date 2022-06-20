package com.devsuperior.hrapigatewayzuul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	@Autowired
	private JwtTokenStore tokenStore;
	
	private static final String[] PUBLIC = { "/hr-oauth/oauth/token" };
	private static final String[] OPERATOR = { "/hr-worker/**" };
	private static final String[] ADMIN = { "/hr-payroll/**", "/hr-user/**" };

	/*
	 * Esse método serve para que o projeto consiga ler um token que pode ser enviado na requisição
	 * para autenticação/autorização	 * 
	 */
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		
		resources.tokenStore(tokenStore);
	}

	
	/*
	 * Esse método é o cara que vai configurar as autorizxações.
	 * 
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		/* antMatchers serve para definir as autorizações, vc pode falar para um étodo http expecifico ou não
		   e pode ir customizandfo as autorizações conforme a necessidade. */
		
		http.authorizeRequests()
		.antMatchers(PUBLIC).permitAll() /* Todos os caminhos definidor na constante PUBLIC 
		vão ter permissões para acessar sem precisar estar logado*/
		
		.antMatchers(HttpMethod.GET, OPERATOR).hasAnyRole("OPERATOR", "ADMIN") /* Aqui só estamos autorizando 
		requisições GET para os caminhos que estiverem na contante OPERATOR e só quem tiver com perfil 
		OPERATOR ou ADMIN podem acessa-las, e somente com o método GET.*/
		
		.antMatchers(ADMIN).hasRole("ADMIN") /*Quem tentar acessar os cmainhos que estiverem na constante ADMIN
		só conseguirá acessar se tiver o perfil ADMIN*/
		
		.anyRequest().authenticated(); /*Qualquer outra rota que não esteja especificado acima
		precisará ser autenticado primeiro, pode acessar mais desde que esteja autenticado.*/
	}
	
	

}
