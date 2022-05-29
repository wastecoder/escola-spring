package br.com.waste.escola.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "disciplinas")
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 60)
    private String nome;
    @Column(nullable = false, length = 3)
    private Integer semestre;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = true)
    private Professor professor;

    @ManyToMany
    @JoinTable(name = "disciplinas_alunos",
                joinColumns = @JoinColumn(name = "disciplinas_fk"),
                inverseJoinColumns = @JoinColumn(name = "alunos_fk"))
    Set<Aluno> alunos;


    @Deprecated
    public Disciplina() {}

    public Disciplina(String nome, Integer semestre, Professor professor, Set<Aluno> alunos) {
        this.nome = nome;
        this.semestre = semestre;
        this.professor = professor;
        this.alunos = alunos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Set<Aluno> getAlunos() {
        return alunos;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", semestre=" + semestre +
                ", professor=" + professor +
                ", alunos=" + alunos +
                '}';
    }
}
