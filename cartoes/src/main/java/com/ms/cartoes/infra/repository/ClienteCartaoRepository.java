package com.ms.cartoes.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.cartoes.domain.CataoCliente;

public interface ClienteCartaoRepository extends JpaRepository<CataoCliente, Long> {
	
	List<CataoCliente> findByCpf(String cpf);

}
