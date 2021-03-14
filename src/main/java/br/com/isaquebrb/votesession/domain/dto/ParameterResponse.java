package br.com.isaquebrb.votesession.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParameterResponse {

    private String name;
    private Integer integerValue;
}
