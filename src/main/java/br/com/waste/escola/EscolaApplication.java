package br.com.waste.escola;

import br.com.waste.escola.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;

@SpringBootApplication
public class EscolaApplication implements CommandLineRunner {
	private final ProfessorService professorService;

	@Autowired
	public EscolaApplication(ProfessorService professorService) {
		this.professorService = professorService;
	}

	public static void main(String[] args) {
		SpringApplication.run(EscolaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner input = new Scanner(System.in);
		boolean continuar = true;

		do {
			System.out.println("\n+----------------+");
			System.out.println("|   ENTIDADES    |");
			System.out.println("+----------------+");
			System.out.println("| 0 - Sair		 |");
			System.out.println("| 1 - Professor  |");
			System.out.println("| 2 - Aluno		 |");
			System.out.println("| 3 - Disciplina |");
			System.out.println("+----------------+");
			System.out.print("Escolha: ");
			byte escolha = input.nextByte();

			switch (escolha) {
				case 1:
					professorService.menu(input);
					break;
				case 2:
					System.out.println("TODO: Aluno");
					break;
				case 3:
					System.out.println("TODO: Disciplina");
					break;
				default:
					continuar = false;
					break;
			}
		} while (continuar);
	}
}
