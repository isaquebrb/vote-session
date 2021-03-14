package br.com.isaquebrb.votesession.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class StandardError {

    private Integer status;

    @Builder.Default
    private List<String> errors = new ArrayList<>();

    private String message;

}
