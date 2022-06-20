package com.devsuperior.hrapigatewayzuul.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/*
 * A anotação @RefreshScope serve para atualizar os valores das variáveis  em tempo de execução
 * os valores estão sendo obtidos no repositorio privado lá no github "MS-Course-ProfNelioAlves-configs"
 * 
 */

@RefreshScope
@Configuration
public class AppConfig {
	
	@Value("${jwt.secret}")
	private String jwtSecret;

	// Esse componente serve para converter a senha recebida pelo cliente num token.
	@Bean
	public JwtAccessTokenConverter accesTokenConverter() {

		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(jwtSecret);

		return tokenConverter;
	}

	// Esse componente serve para ler as informações do token.
	@Bean
	public JwtTokenStore tokenStore() {

		return new JwtTokenStore(accesTokenConverter());
	}

}
