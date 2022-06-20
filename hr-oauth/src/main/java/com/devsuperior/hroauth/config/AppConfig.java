package com.devsuperior.hroauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class AppConfig {
	
	@Value("${jwt.secret}")
	private String jwtSecret;

	// Serve para gerar uma senha criptografada no padrão BCRYPT
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
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
