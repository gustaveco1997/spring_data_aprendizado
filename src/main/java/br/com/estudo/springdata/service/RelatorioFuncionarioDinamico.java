package br.com.estudo.springdata.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.estudo.springdata.orm.Funcionario;
import br.com.estudo.springdata.repository.FuncionarioRepository;
import br.com.estudo.springdata.specification.FuncionarioSpecification;

@Service
public class RelatorioFuncionarioDinamico {
	private final FuncionarioRepository funcionarioRepository;

	public RelatorioFuncionarioDinamico(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	public void inicial(Scanner scanner) {
		System.out.println("Digite o nome");
		String nome = scanner.next();

		System.out.println("Digite o cpf");
		String cpf = scanner.next();

		System.out.println("Digite o salario");
		BigDecimal salario = new BigDecimal(scanner.next());

		System.out.println("Digite a data de contratação");
		String dataContratacaoString = scanner.next();

		if (nome.equalsIgnoreCase("null"))
			nome = null;

		if (cpf.equalsIgnoreCase("null"))
			cpf = null;

		if (salario.equals(BigDecimal.ZERO))
			salario = null;

		LocalDate dataContratacao = null;
		if (dataContratacaoString.equalsIgnoreCase("null"))
			dataContratacao = null;
		else
			dataContratacao = LocalDate.parse(dataContratacaoString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		List<Funcionario> funcionarios = funcionarioRepository.findAll(Specification.where(FuncionarioSpecification
				.nome(nome).or(FuncionarioSpecification.cpf(cpf)).or(FuncionarioSpecification.salario(salario))
				.or(FuncionarioSpecification.dataContratacao(dataContratacao))));

		funcionarios.forEach(funcionario -> {
			System.out.println(funcionario.getId() + " - " + funcionario.getNome() + " - "
					+ funcionario.getCargo().getDescricao());
		});
	}

}
