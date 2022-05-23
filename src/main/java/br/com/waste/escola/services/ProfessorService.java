package br.com.waste.escola.services;

import br.com.waste.escola.models.Professor;
import br.com.waste.escola.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Scanner;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public void menu(Scanner input) {
        boolean continuar = true;

        do {
            System.out.println("\n+---------------+");
            System.out.println("|   PROFESSOR   |");
            System.out.println("+---------------+");
            System.out.println("| 0 - Voltar    |");
            System.out.println("| 1 - Cadastrar |");
            System.out.println("| 2 - Ler       |");
            System.out.println("| 3 - Alterar   |");
            System.out.println("| 4 - Remover   |");
            System.out.println("+---------------+");
            System.out.print("Escolha: ");
            byte escolha = input.nextByte();

            switch (escolha) {
                case 1:
                    this.create(input);
                    break;
                case 2:
                    System.out.println("TODO: read");
                    break;
                case 3:
                    System.out.println("TODO: update");
                    break;
                case 4:
                    System.out.println("TODO: remove");
                    break;
                default:
                    continuar = false;
                    break;
            }
        } while (continuar);
    }

    private void create(Scanner input) {
        input.nextLine();   // Evita o bug de String com espaços
        System.out.println("\n>> CADASTRANDO: professor <<");

        System.out.print("> nome: ");
        String nome = input.nextLine();

        System.out.print("> prontuário: ");
        String prontuario = input.nextLine();

        Professor professor = new Professor(nome, prontuario);
        System.out.println("Professor [" + nome + "] cadastrado com sucesso!");
        professorRepository.save(professor);
    }
}
