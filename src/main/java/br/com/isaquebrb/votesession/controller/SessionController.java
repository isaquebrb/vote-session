package br.com.isaquebrb.votesession.controller;

import br.com.isaquebrb.votesession.domain.dto.SessionResponse;
import br.com.isaquebrb.votesession.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService service;

    @PostMapping("/start/{topicId}")
    public ResponseEntity<SessionResponse> startSession(@PathVariable Long topicId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.startSession(topicId));
    }
}
