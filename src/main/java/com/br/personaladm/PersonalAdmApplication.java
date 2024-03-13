package com.br.personaladm;

import com.br.personaladm.domain.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		System.out.println("inicializando o projeto personal-adm");
	}
}
