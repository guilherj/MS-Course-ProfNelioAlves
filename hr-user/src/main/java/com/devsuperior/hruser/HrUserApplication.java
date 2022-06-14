package com.devsuperior.hruser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableEurekaClient
@SpringBootApplication
public class HrUserApplication implements CommandLineRunner {
	
	/*
	 * Para gerar uma senha criptografada no padrão BCRYPT temos que injetar sua dependência no projeto
	 * porém, o BcryptPasswordEncoder não vem instanciado então devemos criar um @Bean para isso,
	 * por isso criamos uma classe config no pacote config, veja como a classe foi criada.
	 * 
	 * Após criar a classe config para instancia-la aqui, coloca a aplicação com o método main 
	 * implementando a interface CommandLineRunner e no método run coloque o system.out.println
	 * como foi feito abaixo, no console irá aparecer a senha criptografada.
	 * 
	 * É importante fazer isso quando precisar passar alguma senha no projeto, por boas práticas
	 * é bom não passar uma senha assim pura sem nenhuma segurança.
	 * 
	 */
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HrUserApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("Senha BCRYPT: " + passwordEncoder.encode("123456"));
	}

}
