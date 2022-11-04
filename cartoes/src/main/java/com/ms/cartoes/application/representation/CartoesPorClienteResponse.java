package com.ms.cartoes.application.representation;

import java.math.BigDecimal;

import com.ms.cartoes.domain.CataoCliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartoesPorClienteResponse {
	private  String nome;
	private  String bandeira;
	private BigDecimal limiteliberado;

	
	public static CartoesPorClienteResponse fromModel(CataoCliente cliente) {
		return new CartoesPorClienteResponse(
				cliente.getCartao().getNome(),
				cliente.getCartao().getBandeira().toString(),
				cliente.getCartao().getLimiteBasico()
				);
	}

}
