package br.com.waste.escola.services;

import br.com.waste.escola.models.Aluno;
import br.com.waste.escola.repositories.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class RelatorioAlunoService {
    private final AlunoRepository alunoRepository;

    @Autowired
    public RelatorioAlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public void menu(Scanner input) {
        boolean continuar = true;

        do {
            System.out.println("\n+--------------------+");
            System.out.println("|    BUSCAR ALUNO    |");
            System.out.println("+--------------------+");
            System.out.println("| 0 - Voltar         |");
            System.out.println("| 1 - Início do nome |");
            System.out.println("| 2 - 1 + idade <=   |");
            System.out.println("| 3 - 1 + idade >=   |");
            System.out.println("| 4 - 3 + matriculado|");
            System.out.println("+--------------------+");
            System.out.print("Escolha: ");
            byte escolha = input.nextByte();
            input.nextLine();   // Evita o bug de String com espaços

            switch (escolha) {
                case 1:
                    this.byName(input);
                    break;
                case 2:
                    this.byNameAndAgeLessEqual(input);
                    break;
                case 3:
                    this.byNameAndAgeGreatterEqual(input);
                    break;
                case 4:
                    this.byNameAndAgeGreatterEqualAndEnrolled(input);
                    break;
                default:
                    continuar = false;
                    break;
            }
        } while (continuar);
    }

    private void byName(Scanner input) {
        System.out.println("\n>>> BUSCANDO: início do nome <<<");

        System.out.print("> nome: ");
        String nome = input.nextLine();

        List<Aluno> alunosNome = alunoRepository.findByNomeStartingWith(nome);
        alunosNome.forEach(System.out::println);
    }

    private void byNameAndAgeLessEqual(Scanner input) {
        System.out.println("\n>>> BUSCANDO: início do nome e idade <= x <<<");

        System.out.print("> nome: ");
        String nome = input.nextLine();

        System.out.print("> idade: ");
        Integer idade = input.nextInt();

        List<Aluno> alunosNome = alunoRepository.findByNomeStartingWithAndIdadeLessThanEqual(nome, idade);
        alunosNome.forEach(System.out::println);
    }

    private void byNameAndAgeGreatterEqual(Scanner input) {
        System.out.println("\n>>> BUSCANDO: início do nome e idade >= x <<<");

        System.out.print("> nome: ");
        String nome = input.nextLine();

        System.out.print("> idade: ");
        Integer idade = input.nextInt();

        List<Aluno> alunosNome = alunoRepository.findNameEIdadeMaiorIgual(nome, idade);
        alunosNome.forEach(System.out::println);
    }

    private void byNameAndAgeGreatterEqualAndEnrolled(Scanner input) {
        System.out.println("\n>>> BUSCANDO: nome, idade >= x e disciplina <<<");

        System.out.print("> nome da disciplina: ");
        String disciplina = input.nextLine();

        System.out.print("> nome do aluno: ");
        String nome = input.nextLine();

        System.out.print("> idade do aluno: ");
        Integer idade = input.nextInt();


        List<Aluno> alunosNome = alunoRepository.findNameEIdadeMaiorIgualMatriculado(nome, idade, disciplina);
        alunosNome.forEach(System.out::println);
    }
}
