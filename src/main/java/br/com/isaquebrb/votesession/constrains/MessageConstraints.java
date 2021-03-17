package br.com.isaquebrb.votesession.constrains;

public abstract class MessageConstraints {
    public static final String DOCUMENT_NOT_BLANK = "O campo document e obrigatorio";
    public static final String DOCUMENT_SIZE = "O campo document deve conter apenas 11 caracteres";
    public static final String CPF = "O campo document (cpf) e invalido";
    public static final String VALUE_NOT_BLANK = "O campo value e obrigatorio";
    public static final String NAME_NOT_BLANK = "O campo name e obrigatorio";
    public static final String VOTE_CHOICE = "A escolha do voto deve ser 'SIM' ou 'NÃO'";
    public static final String SESSION_ID_NOT_BLANK = "O campo sessionId e obrigatorio";
    public static final String SESSION_ID_POSITIVE =    "O campo sessionId deve ser numerico";
}
