package br.com.isaquebrb.votesession.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopicResponse {

    private Long id;
    private String name;
    private String description;
    private String status;
    private String result;
}
