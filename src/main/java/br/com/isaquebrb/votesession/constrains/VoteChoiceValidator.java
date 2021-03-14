package br.com.isaquebrb.votesession.constrains;

import br.com.isaquebrb.votesession.domain.enums.VoteChoice;
import br.com.isaquebrb.votesession.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class VoteChoiceValidator implements ConstraintValidator<VoteChoiceParams, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        String voteChoice = StringUtils.normalize(value).toUpperCase();

        return voteChoice.equals(VoteChoice.YES.getValue()) ||
                voteChoice.equals(VoteChoice.NO.getValue());
    }
}
