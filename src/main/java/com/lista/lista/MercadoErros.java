package com.lista.lista;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class MercadoErros {
    @ExceptionHandler(UssuarioException.class)
    public ResponseEntity<String> handlerusuarioexception(UssuarioException e) {

        return ResponseEntity.badRequest().body(e.getMessage());
    }

    public static class UssuarioException extends IllegalArgumentException {
        public UssuarioException(String message) {
            super(message);
        }

        @ExceptionHandler(BuscasException.class)
        public ResponseEntity<String> handlerbuscarexception(BuscasException x) {
            return ResponseEntity.badRequest().body(x.getMessage());
        }

        public static class BuscasException extends RuntimeException {
            public BuscasException(String message) {
                super(message);
            }
        }
    }

}
