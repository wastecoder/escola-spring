package br.com.waste.escola.repositories;

import br.com.waste.escola.models.Aluno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends CrudRepository<Aluno, Long> {
        //Derived query
    List<Aluno> findByNomeStartingWith(String nome);

    List<Aluno> findByNomeStartingWithAndIdadeLessThanEqual(String nome, Integer idade);


        //JPQL
    @Query("SELECT a FROM Aluno a WHERE a.nome like :nome% AND a.idade >= :idade")
    List<Aluno> findNameEIdadeMaiorIgual(String nome, Integer idade);

    @Query("SELECT a FROM Aluno a INNER JOIN a.disciplinas d WHERE a.nome like :nomeAluno% AND a.idade >= :idadeAluno AND d.nome like :nomeDisciplina%")
    List<Aluno> findNameEIdadeMaiorIgualMatriculado(String nomeAluno, Integer idadeAluno, String nomeDisciplina);
}
