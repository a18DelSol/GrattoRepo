package com.a18delsol.grattorepo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No se pudo encontrar ningún objeto de con ese ID.")  // 404
public class EntityNotFound extends RuntimeException {
}