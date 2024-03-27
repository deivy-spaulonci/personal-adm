package com.br.personaladm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class PersonalAdmApplication implements CommandLineRunner {

//	@Autowired
//	private ContaRepository contaRepository;

	public static void main(String[] args) {
		SpringApplication.run(PersonalAdmApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		contaRepository.findAll().forEach(System.out::println);
//		System.out.println("inicializando o projeto personal-adm");
	}
}
