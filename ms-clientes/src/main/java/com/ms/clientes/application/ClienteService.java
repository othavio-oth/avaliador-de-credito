package com.ms.clientes.application;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.clientes.domain.Cliente;
import com.ms.clientes.infra.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

	private final ClienteRepository clienteRepository;
	
	@Transactional
	public Cliente save(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public Optional<Cliente> getByCpf(String cpf){
		return clienteRepository.findByCpf(cpf);
	}

}
