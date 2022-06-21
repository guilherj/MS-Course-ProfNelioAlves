package com.devsuperior.hrapigatewayzuul.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	@Autowired
	private JwtTokenStore tokenStore;
	
	private static final String[] PUBLIC = { "/hr-oauth/oauth/token" };
	private static final String[] OPERATOR = { "/hr-worker/**" };
	private static final String[] ADMIN = { "/hr-payroll/**", "/hr-user/**", "/actuator/**", "/hr-worker/actuator/**", "/hr-oauth/actuator/**"};

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
		
		// Chamando os Beans de CORS feito abaixo.
		http.cors().configurationSource(corsConfigurationSource());
	}
	
	
	/* Os Beans abaixo servem para configurar o CORS que é uma medida de segurança que os navegadores tem
	 * de impedir que aplicações acessem outras aplicações de dominios diferentes. Porém como
	 * estamos trabalhando com microserviços e é necessário muitas das vezes que uma aplicação de outro dominio
	 * tenha acesso a eles fazemos essa configuração de CORS para liberar esses acessos.
	 * 
	 * 
	 */	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Arrays.asList("*")); //Qual origem será liberada "*" siginifica todas as origens
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "DELETE", "PATCH")); //Quais metodos serão liberados
		corsConfig.setAllowCredentials(true); //Precisará de credenciais?
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); //Quais cabeçalhos serão liberados?
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		source.registerCorsConfiguration("/**", corsConfig);
		
		return source;
		
	}
	
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		
		FilterRegistrationBean<CorsFilter> bean 
		= new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
		
		// A linha abaixo diz que esse filtro deve ser executado com alta precedência.
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		
		return bean;
	}

}
