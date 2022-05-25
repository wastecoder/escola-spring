package br.com.waste.escola.models;

import javax.persistence.*;

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

    @Deprecated
    public Disciplina() {}

    public Disciplina(String nome, Integer semestre) {
        this.nome = nome;
        this.semestre = semestre;
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
}
