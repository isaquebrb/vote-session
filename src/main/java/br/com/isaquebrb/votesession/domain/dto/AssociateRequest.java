package br.com.isaquebrb.votesession.domain.dto;

import br.com.isaquebrb.votesession.constrains.MessageConstraints;
import br.com.isaquebrb.votesession.domain.Associate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class AssociateRequest {

    @NotBlank(message = MessageConstraints.DOCUMENT_NOT_BLANK)
    @Size(message = MessageConstraints.DOCUMENT_SIZE, min = 11, max = 11)
    @CPF(message = MessageConstraints.CPF)
    private String document;

    private String name;

    public Associate toEntity() {
        return new Associate(document, name);
    }
}
