package br.com.waste.escola;

import br.com.waste.escola.services.DisciplinaService;
import br.com.waste.escola.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;

@SpringBootApplication
public class EscolaApplication implements CommandLineRunner {
	private final ProfessorService professorService;
	private final DisciplinaService disciplinaService;

	@Autowired
	public EscolaApplication(ProfessorService professorService, DisciplinaService disciplinaService) {
		this.professorService = professorService;
		this.disciplinaService = disciplinaService;
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
			System.out.println("|    ENTIDADE    |");
			System.out.println("+----------------+");
			System.out.println("| 0 - Sair		 |");
			System.out.println("| 1 - Professor  |");
			System.out.println("| 2 - Disciplina |");
			System.out.println("| 3 - Aluno		 |");
			System.out.println("+----------------+");
			System.out.print("Escolha: ");
			byte escolha = input.nextByte();

			switch (escolha) {
				case 1:
					professorService.menu(input);
					break;
				case 2:
					disciplinaService.menu(input);
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
