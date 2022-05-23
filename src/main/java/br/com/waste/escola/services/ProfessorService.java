package br.com.waste.escola.services;

import br.com.waste.escola.models.Professor;
import br.com.waste.escola.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
                    this.update(input);
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
        System.out.println("\n>>> CADASTRANDO: professor <<<");

        System.out.print("> nome: ");
        String nome = input.nextLine();

        System.out.print("> prontuário: ");
        String prontuario = input.nextLine();

        Professor professor = new Professor(nome, prontuario);
        System.out.println("Professor [" + nome + "] cadastrado com sucesso!");
        professorRepository.save(professor);
    }

    public void update(Scanner input) {
        input.nextLine();
        System.out.println("\n>>> ALTERANDO: professor <<<");

        System.out.print("> Professor ID: ");
        Long id = input.nextLong();

        Optional<Professor> opt = professorRepository.findById(id);
        if (opt.isPresent()) {
            input.nextLine();
            Professor professor = opt.get();
            System.out.println("\n>>> DICA: deixe vazio para não atualizar <<<");

            System.out.print("> nome: ");
            String nome = input.nextLine();
            if(nome.length() > 0) {
                professor.setNome(nome);
            }

            System.out.print("> prontuário: ");
            String prontuario = input.nextLine();
            if(prontuario.length() > 0){
                professor.setProntuario(prontuario);
            }

            System.out.println("Professor [" + id + "] alterado com sucesso!");
            professorRepository.save(professor);

        }else {
            System.out.println("ERRO: ID [" + id + "] INVÁLIDO!");
        }

    }
}
