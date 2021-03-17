package br.com.isaquebrb.votesession.controller;

import br.com.isaquebrb.votesession.domain.dto.TopicRequest;
import br.com.isaquebrb.votesession.domain.dto.TopicResponse;
import br.com.isaquebrb.votesession.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService service;

    @PostMapping("/new")
    public ResponseEntity<TopicResponse> createTopic(@Valid @RequestBody TopicRequest topic) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createTopic(topic));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponse> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.findById(id).toDto());
    }
}
