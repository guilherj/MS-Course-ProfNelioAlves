package com.devsuperior.hrapigatewayzuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class AppConfig {

	// Esse componente serve para converter a senha recebida pelo cliente num token.
	@Bean
	public JwtAccessTokenConverter accesTokenConverter() {

		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey("MY SECRET KEY");

		return tokenConverter;
	}

	// Esse componente serve para ler as informações do token.
	@Bean
	public JwtTokenStore tokenStore() {

		return new JwtTokenStore(accesTokenConverter());
	}

}
