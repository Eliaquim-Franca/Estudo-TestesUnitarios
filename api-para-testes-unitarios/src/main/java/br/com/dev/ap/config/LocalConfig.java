package br.com.dev.ap.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.dev.ap.domain.User;
import br.com.dev.ap.repositories.UserRepository;

@Configuration
@Profile("local")
public class LocalConfig {
	
	@Autowired
	private UserRepository repository;
	
	@Bean
	public void startDB() {
		User u1 = new User(null, "Silas", "Silas@gmail","123");
		User u2 = new User(null, "Eliaquim", "Eliaquim@gmail","123");
		
		repository.saveAll(List.of(u1,u2));
	}
}
