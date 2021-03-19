package br.com.isaquebrb.votesession.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class TopicResponse {

    private Long id;
    private String name;
    private String description;
    private String status;
    private String result;
}
