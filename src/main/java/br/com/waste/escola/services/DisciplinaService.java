package br.com.waste.escola.services;

import br.com.waste.escola.models.Aluno;
import br.com.waste.escola.models.Disciplina;
import br.com.waste.escola.models.Professor;
import br.com.waste.escola.repositories.AlunoRepository;
import br.com.waste.escola.repositories.DisciplinaRepository;
import br.com.waste.escola.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class DisciplinaService {
    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;

    @Autowired
    public DisciplinaService(DisciplinaRepository disciplinaRepository, ProfessorRepository professorRepository, AlunoRepository alunoRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
    }

    @Transactional
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
            System.out.println("| 6 - Matricular |");
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
                case 6:
                    this.enrollStudent(input);
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

            Set<Aluno> alunos = this.studentLoop(input);
            Disciplina disciplina = new Disciplina(nome, semestre, professor, alunos);

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

    private void enrollStudent(Scanner input) {
        System.out.println("\n>>> MATRICULANDO: aluno em disciplina <<<");

        System.out.print("> Disciplina ID: ");
        Long id = input.nextLong();

        Optional<Disciplina> optionalDisciplina = disciplinaRepository.findById(id);
        if (optionalDisciplina.isPresent()) {
            Disciplina disciplina = optionalDisciplina.get();

            Set<Aluno> alunos = this.studentLoop(input);
            disciplina.getAlunos().addAll(alunos);

            System.out.println("\n>>> Alunos matriculados!");
            disciplinaRepository.save(disciplina);

        } else {
            this.disciplinaErrormessage(id);
        }
    }

    private Set<Aluno> studentLoop(Scanner input) {
        boolean continuar = true;
        Set<Aluno> alunos = new HashSet<>();

        System.out.println("\n>>> MATRICULANDO: alunos <<<");
        System.out.println(">>> DICA: envie 0 para sair\n");
        do {
            System.out.print("> aluno id: ");
            Long alunoId = input.nextLong();

            if (alunoId > 0) {
                Optional<Aluno> optionalAluno = alunoRepository.findById(alunoId);
                if (optionalAluno.isPresent()) {
                    alunos.add(optionalAluno.get());
                } else {
                    this.alunoErrormessage(alunoId);
                }

            } else {
                continuar = false;
            }

        } while (continuar);

        return alunos;
    }


    private void sucessMessage(Long id, String action) {
        System.out.println("\n>>> SUCESSO: disciplina [" + id + "] " + action + "!");
    }

    private void alunoErrormessage(Long id) {
        System.out.println("\n>>> ERRO: aluno [" + id + "] inexistente!");
    }

    private void professorErrormessage(Long id) {
        System.out.println("\n>>> ERRO: professor [" + id + "] inexistente!");
    }

    private void disciplinaErrormessage(Long id) {
        System.out.println("\n>>> ERRO: disciplina [" + id + "] inexistente!");
    }

    @Transactional
    private void showFormatter(Disciplina disciplina) {
        Professor prof = disciplina.getProfessor();
        Set<Aluno> alunos = disciplina.getAlunos();
        String professorDisciplina = "> PROFESSOR [NULL]: NULL";
        if (prof != null) {
            professorDisciplina = "> PROFESSOR [" + prof.getId() + "]: " +  prof.getNome();
        }

        System.out.println("============================");
        System.out.println("> ID: " + disciplina.getId());
        System.out.println("> NOME: " + disciplina.getNome());
        System.out.println("> SEMESTRE: " + disciplina.getSemestre());
        System.out.println(professorDisciplina);
        for (Aluno aluno : alunos) {
            System.out.println("> ALUNO [" + aluno.getId() + "]: " + aluno.getNome());
        }
        System.out.println("============================");
    }
}
