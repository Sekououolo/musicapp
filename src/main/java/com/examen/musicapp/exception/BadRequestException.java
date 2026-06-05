package com.examen.musicapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
// 400 BAD REQUEST : le client a envoyé des données incorrectes
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}