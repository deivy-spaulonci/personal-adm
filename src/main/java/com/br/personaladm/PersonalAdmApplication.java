package com.br.personaladm;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.shell.command.annotation.CommandScan;

import java.util.Base64;

@Log4j2
@SpringBootApplication
//@CommandScan
public class PersonalAdmApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PersonalAdmApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}

}
