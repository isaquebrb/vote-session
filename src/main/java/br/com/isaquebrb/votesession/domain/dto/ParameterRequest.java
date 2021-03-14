package br.com.isaquebrb.votesession.domain.dto;

import br.com.isaquebrb.votesession.constrains.MessageConstraints;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ParameterRequest {

    @NotBlank(message = MessageConstraints.NAME_NOT_BLANK)
    private String name;

    @NotNull(message = MessageConstraints.VALUE_NOT_BLANK)
    private Object value;
}
