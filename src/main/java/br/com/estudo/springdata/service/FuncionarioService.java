package br.com.estudo.springdata.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.estudo.springdata.orm.Cargo;
import br.com.estudo.springdata.orm.Funcionario;
import br.com.estudo.springdata.orm.Unidade;
import br.com.estudo.springdata.repository.CargoRepository;
import br.com.estudo.springdata.repository.FuncionarioRepository;
import br.com.estudo.springdata.repository.UnidadeRepository;

@Service
public class FuncionarioService implements IService {
	private final FuncionarioRepository funcionarioRepository;
	private final UnidadeRepository unidadeRepository;
	private final CargoRepository cargoRepository;
	private boolean system = true;

	public FuncionarioService(FuncionarioRepository funcionarioRepository, 
			UnidadeRepository unidadeRepository,
			CargoRepository cargoRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.unidadeRepository = unidadeRepository;
		this.cargoRepository=cargoRepository;	
	}

	@Override
	public void inicial(Scanner scanner) {
		while (system) {
			System.out.println("\n\nFuncionário  ações: ");
			System.out.println("1 - Salvar");
			System.out.println("2 - Atualizar");
			System.out.println("3 - Visualizar");
			System.out.println("4 - Deletar");
			System.out.println("5 - Deletar todos os registros");
			System.out.println("\n0 - Sair ");
			System.out.println("\n");

			int action = scanner.nextInt();
			switch (action) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;
			case 3:
				visualizarPaginado(scanner);
				break;
			case 4:
				deletar(scanner);
			case 5:
				deleteAll();
				break;
			default:
				system = false;
			}
		}
	}

	@Override
	public void salvar(Scanner scanner) {
		Funcionario funcionario = new Funcionario();

		System.out.println("Nome: ");
		funcionario.setNome(scanner.next());

		System.out.println("CPF: ");
		funcionario.setCpf(scanner.next());

		System.out.println("Unidades: ");
		unidadeRepository.findAll()
				.forEach(unidade -> System.out.println(unidade.getId() + " - " + unidade.getDescricao()));

		List<Unidade> unidadesTrabalho = new ArrayList<>();
		boolean whileUnidade = true;
		while (whileUnidade) {
			System.out.println("Selecione a unidade de trabalho:  (0 - SAIR)");
			Optional<Unidade> unidade = unidadeRepository.findById(scanner.nextInt());
			if (unidade.isPresent())
				unidadesTrabalho.add(unidade.get());
			else
				whileUnidade = false;
		}
		
		funcionario.setUnidades(unidadesTrabalho);

		System.out.println("Cargos: ");
		cargoRepository.findAll().forEach(cargo -> System.out.println(cargo.getId() + " - " + cargo.getDescricao()));

		Optional<Cargo> cargo = Optional.empty();
		while (!cargo.isPresent()) {
			System.out.println("Selecione ao cargo:  (0 - SAIR)");
			cargo = cargoRepository.findById(scanner.nextInt());
			if (!cargo.isEmpty())
				System.out.println("Cargo Inválido!");
		}

		funcionario.setCargo(cargo.get());
		funcionarioRepository.save(funcionario);
		System.out.println("Salvo");

	}

	@Override
	public void atualizar(Scanner scanner) {
		int id = scanner.nextInt();

		Funcionario funcionario = funcionarioRepository.findById(id).orElse(new Funcionario());
		System.out.println("Novo Nome: ");
		funcionario.setNome(scanner.next());

		System.out.println("Novo CPF: ");
		funcionario.setCpf(scanner.next());
		funcionarioRepository.save(funcionario);
	}

	@Override
	public void visualizar() {
		Iterable<Funcionario> funcionarios = funcionarioRepository.findAll();
		funcionarios.forEach(funcionario -> {
			System.out.println(funcionario.toString());
		});
	}
	
	public void visualizarPaginado(Scanner scanner) {
		
		System.out.println("Escolha a página: " );
		Integer page = scanner.nextInt(); 
		
		Pageable pagination = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id"));
		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pagination);
		
		
		System.out.println("Página Atual: " + funcionarios.getNumber());
		System.out.println("Total Páginas: " + funcionarios.getTotalPages());
		System.out.println("Total funcionários: " + funcionarios.getTotalElements());
		
		
		funcionarios.forEach(funcionario -> {
			System.out.println(funcionario.toString());
		});
	}

	@Override
	public void deletar(Scanner scanner) {
		System.out.println("Qual registro deseja deletar? ");
		int id = scanner.nextInt();
		funcionarioRepository.deleteById(id);
		System.out.println("Deletado");
	}

	@Override
	public void deleteAll() {
		funcionarioRepository.deleteAll();
	}

}
