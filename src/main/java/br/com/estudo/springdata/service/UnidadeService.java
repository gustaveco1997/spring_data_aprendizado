package br.com.estudo.springdata.service;

import java.util.Scanner;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import br.com.estudo.springdata.orm.Cargo;
import br.com.estudo.springdata.orm.Unidade;
import br.com.estudo.springdata.repository.UnidadeRepository;

@Service
public class UnidadeService implements IService {
	private final UnidadeRepository unidadeRepository;
	private boolean system = true;

	public UnidadeService(UnidadeRepository unidadeRepository) {
		this.unidadeRepository = unidadeRepository;
	}

	@Override
	public void inicial(Scanner scanner) {
		while (system) {
			System.out.println("\n\nUnidade  ações: ");
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

	@Override
	public void salvar(Scanner scanner) {
		Unidade unidade = new Unidade();
		System.out.print("Nome da Unidade");
		String descricao = scanner.next();

		System.out.println("\nEndereço da Unidade");
		String endereco = scanner.next();
		unidade.setDescricao(descricao);
		unidade.setEndereco(endereco);
		unidadeRepository.save(unidade);

		System.out.println("Salvo");

	}

	@Override
	public void atualizar(Scanner scanner) {
		int id = scanner.nextInt();

		System.out.println("Nova Descricação: ");
		String descricao = scanner.next();

		System.out.println("Novo Endereço: ");
		String endereco = scanner.next();

		Unidade unidade = unidadeRepository.findById(id).orElse(new Unidade());
		unidade.setDescricao(descricao);
		unidade.setEndereco(endereco);
		unidadeRepository.save(unidade);

	}

	@Override
	public void visualizar() {
		Iterable<Unidade> unidades = unidadeRepository.findAll();
		unidades.forEach(unidade -> {
			System.out.println(unidade.toString());
		});

	}

	@Override
	public void deletar(Scanner scanner) {
		System.out.println("Qual registro deseja deletar? ");
		int id = scanner.nextInt();
		unidadeRepository.deleteById(id);
		System.out.println("Deletado");
	}

	@Override
	public void deleteAll() {
		unidadeRepository.deleteAll();
	}

}
