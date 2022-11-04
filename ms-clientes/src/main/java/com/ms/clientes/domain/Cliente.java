package com.ms.clientes.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cpf;
	private String nome;
	private int idade;
	
	
	public Cliente(String cpf, String nome, int idade) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.idade = idade;
	}
	
	

}