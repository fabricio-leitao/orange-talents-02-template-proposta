package br.com.zup.propostarefatorada.validacao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Base64Validator implements ConstraintValidator<Base64, String> {
    @Override
    public void initialize(Base64 constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null || value.isEmpty()){
            return false;
        }

        try{
            java.util.Base64.getDecoder().decode(value);
        } catch (IllegalArgumentException illegalArgumentException){
            return false;
        }

        return true;
    }
}
