package tcc.sgmeabackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import tcc.sgmeabackend.model.enums.Prioridade;
import tcc.sgmeabackend.model.enums.Status;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;


@Data
@Entity
public class Chamado implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@UuidGenerator
	private String id;


	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataAbertura = LocalDate.now();


	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFechamento;

	private Prioridade prioridade;
	private Status status;
	private String titulo;
	private String observacoes;

	@ManyToOne
	private Tecnico tecnico;

	@ManyToOne
	private Funcionario funcionario;

	public Chamado() {
		super();
	}

	public Chamado(String id, Prioridade prioridade, Status status, String titulo, String observacoes, Tecnico tecnico,
			Funcionario funcionario) {
		this.prioridade = prioridade;
		this.status = status;
		this.titulo = titulo;
		this.observacoes = observacoes;
		this.tecnico = tecnico;
		this.funcionario = funcionario;
	}



}



