package com.ms.cartoes.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.cartoes.application.representation.CartaoSaveRequest;
import com.ms.cartoes.application.representation.CartoesPorClienteResponse;
import com.ms.cartoes.domain.Cartao;
import com.ms.cartoes.domain.CataoCliente;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResource {
	
	private final CartaoService cartaoService;
	private final ClienteCartaoService clienteCartaoService;
	
	@GetMapping
	public String status() { 
		return "ok";
	}
	
	@PostMapping
	public ResponseEntity<Cartao> saveCartao(@RequestBody CartaoSaveRequest cartaoRequest) {
		Cartao cartao = cartaoRequest.toModel();
		cartaoService.save(cartao); 
		return ResponseEntity.ok(cartao);
	}
	
	@GetMapping(params = "renda")
	public ResponseEntity<List<Cartao>> getCartoesPorRenda(@RequestParam Long renda){
		List<Cartao> list= cartaoService.getCartoesRendaMenorIgual(renda);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam String cpf){
		List<CataoCliente> list = clienteCartaoService.listCartoesByCpf(cpf);
		List<CartoesPorClienteResponse> cartoesPorClienteResponses = list
				.stream() // Abre métodos lambda
				.map(CartoesPorClienteResponse::fromModel) // Faz a conversão de todos os itens
				.collect(Collectors.toList()); // Adiciona os intens a uma lista
		
		return ResponseEntity.ok(cartoesPorClienteResponses);
	}
}
