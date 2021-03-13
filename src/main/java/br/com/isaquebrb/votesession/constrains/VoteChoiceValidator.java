package br.com.isaquebrb.votesession.constrains;

import br.com.isaquebrb.votesession.domain.enums.VoteChoice;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class VoteChoiceValidator implements ConstraintValidator<VoteChoiceParameters, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return value.equals(VoteChoice.YES.name()) ||
                value.equals(VoteChoice.NO.name());
    }
}
