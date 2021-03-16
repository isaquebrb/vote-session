package br.com.isaquebrb.votesession.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    ENTITY_NOT_FOUND("Entidade nao foi localizada."),
    DATABASE_ERROR("Erro no banco de dados."),
    INVALID_FIELD("Campo invalido."),
    BUSINESS_ERROR("Erro interno.");

    private String message;
}
