package br.com.estudo.springdata.orm;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "unidades")
public class Unidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String descricao;
	private String endereco;

	@ManyToMany(mappedBy = "unidades", fetch = FetchType.EAGER)
	private List<Funcionario> funcionarios;

	@Override
	public String toString() {
		String header = String.format("Descrição: %s \nEndereço: %s\n", descricao, endereco);
		StringBuilder builder = new StringBuilder();
		builder.append(header);

		if (funcionarios != null && !funcionarios.isEmpty()) {
			builder.append("\n*Funcionários:\n");
			funcionarios.forEach(funcionario -> {
				String funcDesc = String.format(" - - %s - %s", funcionario.getNome(),
						funcionario.getCargo().getDescricao());
				builder.append(funcDesc);
			});
		}

		return builder.toString();
	}
}
