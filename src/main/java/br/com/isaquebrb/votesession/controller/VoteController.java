package br.com.isaquebrb.votesession.controller;

import br.com.isaquebrb.votesession.domain.dto.VotingRequest;
import br.com.isaquebrb.votesession.service.AssociateVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/vote")
@RequiredArgsConstructor
public class VoteController {

    private final AssociateVoteService service;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody @Valid VotingRequest request) {
        service.vote(request);
        return ResponseEntity.accepted().build();
    }
}
