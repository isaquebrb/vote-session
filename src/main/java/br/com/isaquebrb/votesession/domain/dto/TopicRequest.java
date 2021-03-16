package br.com.isaquebrb.votesession.domain.dto;

import br.com.isaquebrb.votesession.domain.Topic;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class TopicRequest {

    @NotBlank(message = "O campo name é obrigatório.")
    private String name;

    private String description;

    public Topic toEntity() {
        return Topic.builder()
                .name(name)
                .description(description)
                .build();
    }
}