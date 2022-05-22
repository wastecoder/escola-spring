package br.com.waste.escola;

import br.com.waste.escola.models.Professor;
import br.com.waste.escola.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EscolaApplication implements CommandLineRunner {
	@Autowired
	private ProfessorRepository profRepository;

	public static void main(String[] args) {
		SpringApplication.run(EscolaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Professor prof = new Professor("nomeA", "prontA");
		System.out.println("///ANTES do save():");
		System.out.println(prof);

		profRepository.save(prof);

		System.out.println("///DEPOIS do save():");
		System.out.println(prof);
	}
}
