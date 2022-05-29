package br.com.waste.escola.services;

import br.com.waste.escola.models.Aluno;
import br.com.waste.escola.models.Disciplina;
import br.com.waste.escola.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;

    @Autowired
    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Transactional
    public void menu(Scanner input) {
        boolean continuar = true;

        do {
            System.out.println("\n+---------------+");
            System.out.println("|     ALUNO     |");
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
        System.out.println("\n>>> CADASTRANDO: aluno <<<");

        System.out.print("> nome: ");
        String nome = input.nextLine();

        System.out.print("> idade: ");
        Integer idade = input.nextInt();

        Aluno aluno = new Aluno(nome, idade);
        System.out.println("\n>>> SUCESSO: aluno [" + nome + "] cadastrado!");
        alunoRepository.save(aluno);
    }

    private void showOne(Scanner input) {
        System.out.println("\n>>> DATALHES: aluno <<<");

        System.out.print("> Aluno ID: ");
        Long id = input.nextLong();

        Optional<Aluno> opt = alunoRepository.findById(id);
        if (opt.isPresent()) {
            Aluno aluno = opt.get();
            System.out.println();
            this.showFormatter(aluno);
        } else {
            this.errorMessage(id);
        }
    }

    private void showAll() {
        Iterable<Aluno> alunos = alunoRepository.findAll();

        System.out.println("\n>>> LISTAGEM: aluno <<<\n");
        alunos.forEach(this::showFormatter);
    }

    private void update(Scanner input) {
        System.out.println("\n>>> ALTERANDO: aluno <<<");

        System.out.print("> Aluno ID: ");
        Long id = input.nextLong();

        Optional<Aluno> opt = alunoRepository.findById(id);
        if (opt.isPresent()) {
            input.nextLine();
            Aluno aluno = opt.get();

            System.out.println();
            this.showFormatter(aluno);
            System.out.println(">>> DICA 1: dados do aluno exibidos acima");
            System.out.println(">>> DICA 2: campos vazios não são atualizados\n");

            System.out.print("> nome: ");
            String nome = input.nextLine();
            if(nome.length() > 0) {
                aluno.setNome(nome);
            }

            System.out.print("> idade: ");
            String age = input.nextLine();
            if(age.length() > 0) {
                Integer idade = Integer.parseInt(age);
                aluno.setIdade(idade);
            }

            this.sucessMessage(id, "alterado");
            alunoRepository.save(aluno);

        } else {
            this.errorMessage(id);
        }
    }

    private void delete(Scanner input) {
        System.out.println("\n>>> ALTERANDO: aluno <<<");

        System.out.print("> Aluno ID: ");
        Long id = input.nextLong();

        try {
            alunoRepository.deleteById(id);
            this.sucessMessage(id, "deletado");
        } catch (EmptyResultDataAccessException error) {
            this.errorMessage(id);
            System.out.println(">>> ERROR MESSAGE: " + error);
        }
    }


    private void sucessMessage(Long id, String action) {
        System.out.println("\n>>> SUCESSO: aluno [" + id + "] " + action + "!");
    }

    private void errorMessage(Long id) {
        System.out.println("\n>>> ERRO: aluno [" + id + "] inexistente!");
    }

    @Transactional
    private void showFormatter(Aluno aluno) {
        Set<Disciplina> disciplinaList = aluno.getDisciplinas();
        System.out.println("============================");
        System.out.println("> ID: " + aluno.getId());
        System.out.println("> NOME: " + aluno.getNome());
        System.out.println("> IDADE: " + aluno.getIdade());
        if (disciplinaList != null) {
            for (Disciplina disciplina : disciplinaList) {
                System.out.println("> DISCIPLINA [" + disciplina.getId() + "]: " + disciplina.getNome() + ", " + disciplina.getSemestre());
            }
        }
        System.out.println("============================");
    }
}
