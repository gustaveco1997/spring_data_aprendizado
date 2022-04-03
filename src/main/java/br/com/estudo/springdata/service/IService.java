package br.com.estudo.springdata.service;

import java.util.Scanner;

public interface IService {
	void salvar(Scanner scanner);

	void atualizar(Scanner scanner);

	void visualizar();

	void deletar(Scanner scanner);

	void deleteAll();
	
	void inicial(Scanner scanner);
}
