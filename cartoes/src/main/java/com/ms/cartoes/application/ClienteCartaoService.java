package com.ms.cartoes.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ms.cartoes.domain.ClienteCartao;
import com.ms.cartoes.infra.repository.ClienteCartaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

	private final ClienteCartaoRepository repository;

	List<ClienteCartao> listCartoesByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}
}
