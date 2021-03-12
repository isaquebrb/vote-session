package br.com.isaquebrb.votesession.domain.dto;

import br.com.isaquebrb.votesession.domain.Associate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class AssociateRequest {

    @NotBlank(message = "O campo document é obrigatório")
    @Size(message = "O campo document deve conter apenas 11 caracteres", min = 11, max = 11)
    @CPF(message = "O campo document (cpf) é inválido")
    private String document;

    private String name;

    public Associate toEntity() {
        return Associate.builder()
                .document(document)
                .name(name)
                .build();
    }
}
