package br.com.zup.propostarefatorada.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadronizado> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) { // CDD 1 - Classe ErroPadronizado

        Collection<String> mensagens = new ArrayList<>();
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        fieldErrors.forEach(
                fieldError -> {
                    String message = String.format("Campo %s %s", fieldError.getField(), fieldError.getDefaultMessage());
                    mensagens.add(message);
                }
        );

        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadronizado);

    }*/

    @ExceptionHandler
    public ResponseEntity<ErroPadronizado> handleResponseStatusException(ResponseStatusException responseStatusException) {
        Collection<String> mensagens = new ArrayList<>();
        mensagens.add(responseStatusException.getReason());

        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return ResponseEntity.status(responseStatusException.getStatus()).body(erroPadronizado);
    }

    @ExceptionHandler
    public ResponseEntity<ErroPadronizado> handleResponseStatusException(IllegalStateException validationException) {
        Collection<String> mensagens = new ArrayList<>();
        mensagens.add(validationException.getMessage());

        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadronizado);
    }

    @ExceptionHandler
    public ResponseEntity<ErroPadronizado> handleResponseStatusException(FeignException feignException) {
        Collection<String> mensagens = new ArrayList<>();
        mensagens.add(feignException.getMessage());

        ErroPadronizado erroPadronizado = new ErroPadronizado(mensagens);
        return ResponseEntity.status(feignException.status()).body(erroPadronizado);
    }
}
