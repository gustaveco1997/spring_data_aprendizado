package br.com.estudo.springdata.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.estudo.springdata.orm.Funcionario;
import br.com.estudo.springdata.orm.FuncionarioProjecao;
import br.com.estudo.springdata.repository.FuncionarioRepository;

@Service
public class RelatorioService {
	private final FuncionarioRepository funcionarioRepository;
	private boolean system = true;

	public RelatorioService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	public void inicial(Scanner scanner) {
		while (system) {
			System.out.println("\n\nCargo ações: ");
			System.out.println("1 - Busca funcionário por nome");
			System.out.println("2 - Busca funcionário nome/salário/contratacao");
			System.out.println("3 - Busca funcionário por cargo");
			System.out.println("4 - Pesquisa funcionário salário");
			System.out.println("\n0 - Sair ");
			System.out.println("\n");

			int action = scanner.nextInt();
			switch (action) {
			case 1:
				buscaFuncionarioPorNome(scanner);
				break;
			case 2:
				buscaFuncionarioByNomeSalarioMaiorData(scanner);
				break;
			case 3:
				buscaFuncionarioByCargo(scanner);
				break;
			case 4:
				pesquisaFuncionarioSalario();
				break;
//			case 5:
//				deleteAll();
//				break;
			default:
				system = false;
			}
		}
	}

	private void buscaFuncionarioPorNome(Scanner scanner) {
		System.out.println("Digite o nome do(a) funcionário(a): ");
		String nome = scanner.next();

		List<Funcionario> funcionarios = funcionarioRepository.findByNome(nome);
		showFuncionarios(funcionarios);
	}

	private void buscaFuncionarioByNomeSalarioMaiorData(Scanner scanner) {
		System.out.println("Digite o nome do(a) funcionário(a): ");
		String nome = scanner.next();

		System.out.println("Digite o salário: ");
		String salarioString = scanner.next();
		BigDecimal salario = new BigDecimal(salarioString);

		System.out.println("Digite a data de contratação: ");
		String dataContratacaoString = scanner.next();
		LocalDate dataContratacao = LocalDate.parse(dataContratacaoString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		List<Funcionario> funcionarios = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario,
				dataContratacao);
		showFuncionarios(funcionarios);
	}

	private void buscaFuncionarioByCargo(Scanner scanner) {
		System.out.println("Digite o nome do cargo: ");
		String cargo = scanner.next();

		List<Funcionario> funcionarios = funcionarioRepository.findByCargo(cargo);
		showFuncionarios(funcionarios);
	}

	private void pesquisaFuncionarioSalario() {
		List<FuncionarioProjecao> funcionarios = funcionarioRepository.findFuncionarioSalario();
		funcionarios.forEach(funcionario -> {
			System.out.println(funcionario.getId() + " - " + funcionario.getNome() + " - " + funcionario.getSalario());
		});
	}

	private void showFuncionarios(List<Funcionario> funcionarios) {
		funcionarios.forEach(funcionario -> {
			System.out.println(funcionario.toString());
		});
	}
}
