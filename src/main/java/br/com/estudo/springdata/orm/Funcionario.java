package br.com.estudo.springdata.orm;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Funcionario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;
	private String cpf;
	private BigDecimal salario;
	private LocalDate dataContratacao;

	@Fetch(FetchMode.SELECT)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "funcionarios_unidades", joinColumns = {
			@JoinColumn(name = "fk_funcionario") }, inverseJoinColumns = {
					@JoinColumn(name =  "fk_unidade") })
	private List<Unidade> unidades;

	@ManyToOne
	@JoinColumn(name = "cargo_id", nullable = false)
	private Cargo cargo;
	
	@Override
	public String toString() {
		String separador = " - ";
		StringBuilder builder = new StringBuilder();
		builder.append(id).append(separador);
		builder.append(nome).append(separador);
		builder.append(cpf).append(separador);
		builder.append(cargo.getDescricao()).append(separador);
		builder.append(salario);
		return builder.toString();
	}

}
