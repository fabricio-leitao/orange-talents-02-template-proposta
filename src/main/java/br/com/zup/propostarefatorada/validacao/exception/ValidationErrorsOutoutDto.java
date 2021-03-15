package br.com.zup.propostarefatorada.validacao.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorsOutoutDto {

    private List<String> globalErrorsMessages = new ArrayList<>();
    private List<FieldErrorOutputDto> fieldErrors = new ArrayList<>();

    public void addError(String message) {
        globalErrorsMessages.add(message);
    }

    public void addFieldError(String field, String message) {
        FieldErrorOutputDto fieldError = new FieldErrorOutputDto(field, message);
        fieldErrors.add(fieldError);
    }

    public List<String> getGlobalErrorMessages(){
        return globalErrorsMessages;
    }

    public List<FieldErrorOutputDto> getErrors(){
        return fieldErrors;
    }
}