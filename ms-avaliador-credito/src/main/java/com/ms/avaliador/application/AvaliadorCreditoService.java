package com.ms.avaliador.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ms.avaliador.application.exception.DadosClienteNotFoundException;
import com.ms.avaliador.application.exception.ErroComunicacaoMicroservicesException;
import com.ms.avaliador.domain.Cartao;
import com.ms.avaliador.domain.CartaoAprovado;
import com.ms.avaliador.domain.CartaoCliente;
import com.ms.avaliador.domain.DadosCliente;
import com.ms.avaliador.domain.RetornoAvaliacaoCliente;
import com.ms.avaliador.domain.SituacaoCliente;
import com.ms.avaliador.infra.clients.CartaoResourceClient;
import com.ms.avaliador.infra.clients.ClienteResourceClient;

import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

	private final ClienteResourceClient resourceClient;
	private final CartaoResourceClient cartaoResource;

	public SituacaoCliente obterDadosCliente(String cpf)
			throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {

		try {
			ResponseEntity<DadosCliente> dadosClienteResponse = resourceClient.dadosCliente(cpf);
			ResponseEntity<List<CartaoCliente>> dadosCartaoResponse = cartaoResource.getCartoesByCliente(cpf);
			return SituacaoCliente.builder().cliente(dadosClienteResponse.getBody())
					.cartoes(dadosCartaoResponse.getBody()).build();
		} catch (FeignClientException e) {

			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}

			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);

		}

	}

	public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
			throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {

		try {
			ResponseEntity<DadosCliente> dadosClienteResponse = resourceClient.dadosCliente(cpf);
			ResponseEntity<List<Cartao>> cartoesResponse = cartaoResource.getCartoesPorRenda(renda);

			List<Cartao> cartoes = cartoesResponse.getBody();
			List<CartaoAprovado> listCartoesAprovados = cartoes.stream().map(cartao -> {
				BigDecimal limiteBasico = cartao.getLimiteBasico();
				BigDecimal idadeBd = BigDecimal.valueOf(dadosClienteResponse.getBody().getIdade());
				BigDecimal fator = idadeBd.divide(BigDecimal.TEN);
				BigDecimal limiteAprovado = fator.multiply(limiteBasico);

				CartaoAprovado aprovado = new CartaoAprovado();
				aprovado.setCartao(cartao.getNome());
				aprovado.setBandeira(cartao.getBandeira());
				aprovado.setLimiteAprovado(limiteAprovado);
				return aprovado;
			}).collect(Collectors.toList());

			return new RetornoAvaliacaoCliente(listCartoesAprovados);
		} catch (FeignClientException e) {

			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}

			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);

		}

	}

}
