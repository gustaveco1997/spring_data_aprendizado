package br.com.estudo.springdata;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.estudo.springdata.service.CargoService;
import br.com.estudo.springdata.service.FuncionarioService;
import br.com.estudo.springdata.service.RelatorioFuncionarioDinamico;
import br.com.estudo.springdata.service.RelatorioService;
import br.com.estudo.springdata.service.UnidadeService;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

	private final CargoService cargoService;
	private final UnidadeService unidadeService;
	private final FuncionarioService funcionarioService;
	private final RelatorioService relatorioService;
	private final RelatorioFuncionarioDinamico relatorioFuncionarioDinamico;
	private boolean system = true;

	public SpringDataApplication(CargoService cargoService, FuncionarioService funcionarioService,
			UnidadeService unidadeService, RelatorioService relatorioService,
			RelatorioFuncionarioDinamico relatorioFuncionarioDinamico

	) {
		this.cargoService = cargoService;
		this.funcionarioService = funcionarioService;
		this.unidadeService = unidadeService;
		this.relatorioService = relatorioService;
		this.relatorioFuncionarioDinamico = relatorioFuncionarioDinamico;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// é executado após a inicialização do método main
		Scanner scanner = new Scanner(System.in);
		while (system) {
			System.out.println("Ações: ");
			System.out.println(" 0 - Sair ");
			System.out.println(" 1 - Cargo ");
			System.out.println(" 2 - Unidade ");
			System.out.println(" 3 - Funcionário ");
			System.out.println(" 4 - Relatórios");
			System.out.println(" 5 - Relatórios Dinâmicos");

			int action = scanner.nextInt();

			switch (action) {
			case 1:
				cargoService.inicial(scanner);
				break;
			case 2:
				unidadeService.inicial(scanner);
				break;
			case 3:
				funcionarioService.inicial(scanner);
				break;
			case 4:
				relatorioService.inicial(scanner);
				break;
			case 5:
				relatorioFuncionarioDinamico.inicial(scanner);
				break;
			default:
				system = false;
			}
		}
	}
}
