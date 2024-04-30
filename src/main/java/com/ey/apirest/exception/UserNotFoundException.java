package com.ey.apirest.exception;

import java.text.MessageFormat;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String campo) {
        super(MessageFormat.format("No se ha encontrado el usuario: {0}", campo));
    }
}
