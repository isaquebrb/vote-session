package br.com.isaquebrb.votesession.constrains;

public abstract class MessageConstraints {
    public static final String VOTE_CHOICE = "A escolha do voto deve ser 'YES' ou 'NO'";
    public static final String DOCUMENT_NOT_BLANK = "O campo document e obrigatorio";
    public static final String DOCUMENT_SIZE = "O campo document deve conter apenas 11 caracteres";
    public static final String CPF = "O campo document (cpf) e invalido";
}
