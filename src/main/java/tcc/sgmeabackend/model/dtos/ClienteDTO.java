package tcc.sgmeabackend.model.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import tcc.sgmeabackend.model.Funcionario;
import tcc.sgmeabackend.model.enums.UserRole;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected String id;
	@NotNull(message = "O campo NOME é requerido")
	protected String nome;
	@NotNull(message = "O campo CPF é requerido")
	@CPF
	protected String cpf;
	@NotNull(message = "O campo EMAIL é requerido")
	protected String email;
	@NotNull(message = "O campo SENHA é requerido")
	protected String senha;
	protected Set<Integer> perfis = new HashSet<>();

	@JsonFormat(pattern = "dd/MM/yyyy")
	protected LocalDate dataCriacao = LocalDate.now();

	public ClienteDTO() {
		super();
		addPerfil(UserRole.FUNCIONARIO);
	}

	public ClienteDTO(Funcionario obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cpf = obj.getCpf();
		this.email = obj.getEmail();
		this.senha = obj.getSenha();
		this.dataCriacao = obj.getDataCriacao();
		addPerfil(UserRole.FUNCIONARIO);
	}



	public Set<UserRole> getPerfis() {
		return perfis.stream().map(x -> UserRole.toEnum(x)).collect(Collectors.toSet());
	}

	public void addPerfil(UserRole userRole) {
		this.perfis.add(userRole.getCodigo());
	}



}
