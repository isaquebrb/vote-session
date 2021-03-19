package br.com.isaquebrb.votesession.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TopicResult {

    NOT_VOTED("NAO FOI VOTADO"),
    APPROVED("APROVADO"),
    REJECTED("RECUSADO");

    private String label;
}
