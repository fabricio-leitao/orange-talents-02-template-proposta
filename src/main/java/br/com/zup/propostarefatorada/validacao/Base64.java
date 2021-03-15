package br.com.zup.propostarefatorada.validacao;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {Base64Validator.class})
public @interface Base64 {

    String message() default "Biometria no formato inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
