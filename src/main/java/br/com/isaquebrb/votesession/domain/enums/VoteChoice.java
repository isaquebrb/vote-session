package br.com.isaquebrb.votesession.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VoteChoice {

    YES("SIM"),
    NO("NAO");

    private String value;
}
