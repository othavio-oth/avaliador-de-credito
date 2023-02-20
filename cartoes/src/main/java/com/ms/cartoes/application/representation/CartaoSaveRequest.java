package com.ms.cartoes.application.representation;

import java.math.BigDecimal;

import com.ms.cartoes.domain.Bandeira;
import com.ms.cartoes.domain.Cartao;

import lombok.Data;

@Data
public class CartaoSaveRequest {

	private String nome;
	private Bandeira bandeira;
	private BigDecimal renda;
	private BigDecimal limite;

	public Cartao toModel() {
		return new Cartao(nome, bandeira, renda, limite);
	}

}
