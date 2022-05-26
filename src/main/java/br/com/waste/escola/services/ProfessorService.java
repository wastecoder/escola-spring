package br.com.waste.escola.services;

import br.com.waste.escola.models.Professor;
import br.com.waste.escola.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
            System.out.println("| 2 - Consultar |");
            System.out.println("| 3 - Listar    |");
            System.out.println("| 4 - Alterar   |");
            System.out.println("| 5 - Remover   |");
            System.out.println("+---------------+");
            System.out.print("Escolha: ");
            byte escolha = input.nextByte();
            input.nextLine();   // Evita o bug de String com espaços

            switch (escolha) {
                case 1:
                    this.create(input);
                    break;
                case 2:
                    this.showOne(input);
                    break;
                case 3:
                    this.showAll();
                    break;
                case 4:
                    this.update(input);
                    break;
                case 5:
                    this.delete(input);
                    break;
                default:
                    continuar = false;
                    break;
            }
        } while (continuar);
    }


    private void create(Scanner input) {
        System.out.println("\n>>> CADASTRANDO: professor <<<");

        System.out.print("> nome: ");
        String nome = input.nextLine();

        System.out.print("> prontuário: ");
        String prontuario = input.nextLine();

        Professor professor = new Professor(nome, prontuario);
        System.out.println("\n>>> SUCESSO: professor [" + nome + "] cadastrado!");
        professorRepository.save(professor);
    }

    private void showOne(Scanner input) {
        System.out.println("\n>>> DATALHES: professor <<<");

        System.out.print("> Professor ID: ");
        Long id = input.nextLong();

        Optional<Professor> opt = professorRepository.findById(id);
        if (opt.isPresent()) {
            Professor professor = opt.get();
            System.out.println();
            this.showFormatter(professor);
        } else {
            this.errorMessage(id);
        }
    }

    private void showAll() {
        Iterable<Professor> professors = professorRepository.findAll();

        System.out.println("\n>>> LISTAGEM: professor <<<\n");
        professors.forEach(this::showFormatter);
    }

    private void update(Scanner input) {
        System.out.println("\n>>> ALTERANDO: professor <<<");

        System.out.print("> Professor ID: ");
        Long id = input.nextLong();

        Optional<Professor> opt = professorRepository.findById(id);
        if (opt.isPresent()) {
            input.nextLine();
            Professor professor = opt.get();

            System.out.println();
            this.showFormatter(professor);
            System.out.println(">>> DICA 1: dados do professor exibidos acima");
            System.out.println(">>> DICA 2: campos vazios não são atualizados\n");

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

            this.sucessMessage(id, "alterado");
            professorRepository.save(professor);

        }else {
            this.errorMessage(id);
        }
    }

    private void delete(Scanner input) {
        System.out.println("\n>>> DELETANDO: professor <<<");

        System.out.print("> Professor ID: ");
        Long id = input.nextLong();

        try {
            professorRepository.deleteById(id);
            this.sucessMessage(id, "deletado");

        } catch (EmptyResultDataAccessException error) {
            this.errorMessage(id);
            System.out.println(">>> ERROR MESSAGE: " + error);
        }
    }


    private void sucessMessage(Long id, String action) {
        System.out.println("\n>>> SUCESSO: professor [" + id + "] " + action + "!");
    }

    private void errorMessage(Long id) {
        System.out.println("\n>>> ERRO: professor [" + id + "] inexistente!");
    }

    private void showFormatter(Professor professor) {
        System.out.println("===========================");
        System.out.println("> ID: " + professor.getId());
        System.out.println("> NOME: " + professor.getNome());
        System.out.println("> PRONTUÁRIO: " + professor.getProntuario());
        System.out.println("===========================");
    }
}
