package com.ms.avaliador.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.avaliador.application.exception.DadosClienteNotFoundException;
import com.ms.avaliador.application.exception.ErroComunicacaoMicroservicesException;
import com.ms.avaliador.domain.DadosAvaliacao;
import com.ms.avaliador.domain.RetornoAvaliacaoCliente;
import com.ms.avaliador.domain.SituacaoCliente;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("avaliacoes-credito")
public class AvaliadorCreditoController {

	private final AvaliadorCreditoService avaliadorCreditoService;

	@SuppressWarnings("rawtypes")
	@GetMapping(value = "situacao-cliente", params = "cpf")
	public ResponseEntity getSituacao(@RequestParam String cpf) {
		SituacaoCliente situacaoCliente;
		try {
			situacaoCliente = avaliadorCreditoService.obterDadosCliente(cpf);
			return ResponseEntity.ok(situacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoMicroservicesException e) {
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}

	}

	@SuppressWarnings("rawtypes")
	@PostMapping
	public ResponseEntity realizarAvalizacao(@RequestBody DadosAvaliacao dados) {

		try {
			RetornoAvaliacaoCliente avaliacaoCliente = avaliadorCreditoService.realizarAvaliacao(dados.getCpf(),
					dados.getRenda());
			return ResponseEntity.ok(avaliacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoMicroservicesException e) {
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}

}
