package com.ms.avaliador.infra.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ms.avaliador.domain.Cartao;
import com.ms.avaliador.domain.CartaoCliente;

@FeignClient(value = "mscartoes", path = "/cartoes")
public interface CartaoResourceClient {

	@GetMapping(params = "cpf")
	public ResponseEntity<List<CartaoCliente>> getCartoesByCliente(@RequestParam String cpf);

	@GetMapping(params = "renda")
	public ResponseEntity<List<Cartao>> getCartoesPorRenda(@RequestParam Long renda);

}
