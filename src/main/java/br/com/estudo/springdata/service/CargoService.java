package br.com.estudo.springdata.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.estudo.springdata.orm.Cargo;
import br.com.estudo.springdata.repository.CargoRepository;

@Service
public class CargoService {
	private final CargoRepository cargoRepository;
	private boolean system = true;

	public CargoService(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}

	public void inicial(Scanner scanner) {
		while (system) {
			System.out.println("\n\nCargo ações: ");
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
				visualizar();
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

	private void salvar(Scanner scanner) {
		System.out.println("Descrição: ");
		String descricao = scanner.next();

		Cargo cargo = new Cargo();
		cargo.setDescricao(descricao);
		cargoRepository.save(cargo);

		System.out.println("Cargo Salvo");
	}

	private void atualizar(Scanner scanner) {
		System.out.println("Id do registro: ");
		int id = scanner.nextInt();

		System.out.println("Nova Descricação: ");
		String descricao = scanner.next();

//		Cargo cargo = cargoRepository.findById(id).orElse(null);
		Cargo cargo = new Cargo();
		cargo.setId(id);
		cargo.setDescricao(descricao);
		cargoRepository.save(cargo);
	}

	private void visualizar() {
		Iterable<Cargo> cargos = cargoRepository.findAll();
		cargos.forEach(cargo -> System.out.println(cargo.toString()));
	}

	private void deletar(Scanner scanner) {
		System.out.println("Qual registro deseja deletar? ");
		int id = scanner.nextInt();
		cargoRepository.deleteById(id);
		System.out.println("Deletado");
	}

	private void deleteAll() {
		cargoRepository.deleteAll();
	}

}
