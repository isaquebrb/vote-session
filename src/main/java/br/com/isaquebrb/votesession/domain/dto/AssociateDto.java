package br.com.isaquebrb.votesession.model.dto;

import br.com.isaquebrb.votesession.model.Associate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AssociateDto {

    public AssociateDto(Associate associate){
        this.document = associate.getDocument();
        this.name = associate.getName();
    }

    @NotBlank(message = "O campo document é obrigatório")
    @Size(message = "O campo document deve conter apenas 11 caracteres", min = 11, max = 11)
    @CPF(message = "O campo document (cpf) é inválido")
    private String document;

    private String name;

    public Associate toEntity(){
        return Associate.builder()
                .document(document)
                .name(name)
                .build();
    }
}
