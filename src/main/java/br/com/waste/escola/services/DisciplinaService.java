package br.com.waste.escola.services;

import br.com.waste.escola.models.Disciplina;
import br.com.waste.escola.models.Professor;
import br.com.waste.escola.repositories.DisciplinaRepository;
import br.com.waste.escola.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
public class DisciplinaService {
    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;

    @Autowired
    public DisciplinaService(DisciplinaRepository disciplinaRepository, ProfessorRepository professorRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
    }

    public void menu(Scanner input) {
        boolean continuar = true;

        do {
            System.out.println("\n+----------------+");
            System.out.println("|   DISCIPLINA   |");
            System.out.println("+----------------+");
            System.out.println("| 0 - Voltar     |");
            System.out.println("| 1 - Cadastrar  |");
            System.out.println("| 2 - Consultar  |");
            System.out.println("| 3 - Listar     |");
            System.out.println("| 4 - Alterar    |");
            System.out.println("| 5 - Remover    |");
            System.out.println("+----------------+");
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
        System.out.println("\n>>> CADASTRANDO: disciplina <<<");

        System.out.print("> nome: ");
        String nome = input.nextLine();

        System.out.print("> semestre [1,2]: ");
        Integer semestre = input.nextInt();

        System.out.print("> professor id: ");
        Long professorId = input.nextLong();

        Optional<Professor> optionalProfessor = professorRepository.findById(professorId);
        if (optionalProfessor.isPresent()) {
            Professor professor = optionalProfessor.get();
            Disciplina disciplina = new Disciplina(nome, semestre, professor);

            System.out.println("\n>>> SUCESSO: disciplina [" + nome + "] cadastrada!");
            disciplinaRepository.save(disciplina);
        } else {
            this.professorErrormessage(professorId);
        }
    }

    private void showOne(Scanner input) {
        System.out.println("\n>>> DATALHES: disciplina <<<");

        System.out.print("> Disciplina ID: ");
        Long disciplinaId = input.nextLong();

        Optional<Disciplina> optionalDisciplina = disciplinaRepository.findById(disciplinaId);
        if (optionalDisciplina.isPresent()) {
            Disciplina disciplina = optionalDisciplina.get();
            System.out.println();
            this.showFormatter(disciplina);
        } else {
            this.disciplinaErrormessage(disciplinaId);
        }
    }

    private void showAll() {
        Iterable<Disciplina> disciplinas = disciplinaRepository.findAll();

        System.out.println("\n>>> LISTAGEM: disciplina <<<\n");
        disciplinas.forEach(this::showFormatter);
    }

    private void update(Scanner input) {
        System.out.println("\n>>> ALTERANDO: disciplina <<<");

        System.out.print("> Disciplina ID: ");
        Long disciplinaId = input.nextLong();

        Optional<Disciplina> optionalDisciplina = disciplinaRepository.findById(disciplinaId);
        if (optionalDisciplina.isPresent()) {
            input.nextLine();
            Disciplina disciplina = optionalDisciplina.get();

            System.out.println();
            this.showFormatter(disciplina);
            System.out.println(">>> DICA 1: dados da disciplina exibidos acima");
            System.out.println(">>> DICA 2: campos vazios não são atualizados\n");

            System.out.print("> nome: ");
            String nome = input.nextLine();
            if (nome.length() > 0) {
                disciplina.setNome(nome);
            }

            System.out.print("> semestre [1,2]: ");
            String semest = input.nextLine();
            if (semest.length() > 0){
                // Casting para a dica 2 funcionar com números
                Integer semestre = Integer.parseInt(semest);
                disciplina.setSemestre(semestre);
            }

            System.out.print("> professor id: ");
            String profId = input.nextLine();
            if (profId.length() > 0) {
                Long professorId = Long.parseLong(profId);
                Optional<Professor> optionalProfessor = professorRepository.findById(professorId);

                if (optionalProfessor.isPresent()) {
                    Professor professor = optionalProfessor.get();
                    disciplina.setProfessor(professor);
                } else {
                    this.professorErrormessage(professorId);
                }
            }

            this.sucessMessage(disciplinaId, "alterada");
            disciplinaRepository.save(disciplina);

        }else {
            this.disciplinaErrormessage(disciplinaId);
        }
    }

    private void delete(Scanner input) {
        System.out.println("\n>>> DELETANDO: disciplina <<<");

        System.out.print("> Disciplina ID: ");
        Long id = input.nextLong();

        try {
            disciplinaRepository.deleteById(id);
            this.sucessMessage(id, "deletada");

        } catch (EmptyResultDataAccessException error) {
            this.disciplinaErrormessage(id);
            System.out.println(">>> ERROR MESSAGE: " + error);
        }
    }


    private void sucessMessage(Long id, String action) {
        System.out.println("\n>>> SUCESSO: disciplina [" + id + "] " + action + "!");
    }

    private void professorErrormessage(Long id) {
        System.out.println("\n>>> ERRO: professor [" + id + "] inexistente!");
    }

    private void disciplinaErrormessage(Long id) {
        System.out.println("\n>>> ERRO: disciplina [" + id + "] inexistente!");
    }

    private void showFormatter(Disciplina disciplina) {
        Professor prof = disciplina.getProfessor();
        System.out.println("============================");
        System.out.println("> ID: " + disciplina.getId());
        System.out.println("> NOME: " + disciplina.getNome());
        System.out.println("> SEMESTRE: " + disciplina.getSemestre());
        System.out.println("> PROFESSOR [" + prof.getId() + "]: " +  prof.getNome());
        System.out.println("============================");
    }
}
