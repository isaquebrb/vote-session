package br.com.isaquebrb.votesession.domain.enums.parameters;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IntegerParameter {

    SESSION_DURATION_MINUTES("Duração da sessão", 1);

    private String description;
    private Integer defaultValue;

}
