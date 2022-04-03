package br.com.estudo.springdata.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.estudo.springdata.orm.Funcionario;
import br.com.estudo.springdata.orm.FuncionarioProjecao;

@Repository
public interface FuncionarioRepository
		extends PagingAndSortingRepository<Funcionario, Integer>, JpaSpecificationExecutor<Funcionario> {
	List<Funcionario> findByNome(String nome);

	@Query("SELECT f FROM Funcionario f where f.cargo.descricao = :cargo")
	List<Funcionario> findByCargo(String cargo);

	@Query("SELECT f FROM Funcionario f where f.nome = :nome and f.salario > :salario and f.dataContratacao = :dataContratacao")
	List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, BigDecimal salario, LocalDate dataContratacao);

	// exemplo grande, acima está simplificado
	List<Funcionario> findByNomeAndSalarioGreaterThanAndDataContratacao(String nome, BigDecimal salario,
			LocalDate dataContratacao);

	// Projeção (based projection)
	// Cria uma interface com os getters
	@Query(value = "SELECT f.id, f.nome, f.salario FROM funcionario f", nativeQuery = true)
	List<FuncionarioProjecao> findFuncionarioSalario();
	
	//Specification
	
}
