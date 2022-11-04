package com.ms.clientes.application.representation;

import com.ms.clientes.domain.Cliente;

import lombok.Data;

@Data
public class ClienteSaveRequest {
	 
	private String cpf;
	private String nome;
	private int idade;
	
	public Cliente toModel() {
		return new Cliente(cpf, nome, idade);
	}

}