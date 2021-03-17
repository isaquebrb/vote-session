package br.com.isaquebrb.votesession.domain.dto;

import br.com.isaquebrb.votesession.constrains.MessageConstraints;
import br.com.isaquebrb.votesession.constrains.VoteChoiceParams;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class VotingRequest {

    @NotBlank(message = MessageConstraints.DOCUMENT_NOT_BLANK)
    @Size(message = MessageConstraints.DOCUMENT_SIZE, min = 11, max = 11)
    @CPF(message = MessageConstraints.CPF)
    private String document;

    @VoteChoiceParams
    private String voteChoice;

    @NotNull(message = MessageConstraints.SESSION_ID_NOT_BLANK)
    @Positive(message = MessageConstraints.SESSION_ID_POSITIVE)
    private Long sessionId;
}
