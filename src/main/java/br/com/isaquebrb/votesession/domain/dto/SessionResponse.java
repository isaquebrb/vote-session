package br.com.isaquebrb.votesession.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionResponse {

    private Long id;
    private String startDate;
    private String topicName;
}
