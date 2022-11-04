package com.ms.cartoes.infra.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.cartoes.domain.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

	List<Cartao> findByRendaLessThanEqual(BigDecimal renda);

}
