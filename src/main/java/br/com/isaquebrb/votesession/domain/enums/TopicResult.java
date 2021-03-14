package br.com.isaquebrb.votesession.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TopicResult {

    APPROVED("APROVADO"),
    REJECTED("RECUSADO");

    private String label;
}
