package br.com.isaquebrb.votesession.constrains;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = VoteChoiceValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
@Documented
public @interface VoteChoiceParameters {

    String message() default
            MessageConstraints.VOTE_CHOICE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
