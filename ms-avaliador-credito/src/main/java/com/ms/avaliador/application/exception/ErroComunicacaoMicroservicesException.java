package com.ms.avaliador.application.exception;

import lombok.Getter;

public class ErroComunicacaoMicroservicesException extends Exception {
	
	@Getter
	private Integer status;

	public ErroComunicacaoMicroservicesException(String message, Integer status) {
		super(message);
		this.status = status;
	}

}
