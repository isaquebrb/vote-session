package br.com.isaquebrb.votesession.controller;

import br.com.isaquebrb.votesession.domain.dto.ParameterRequest;
import br.com.isaquebrb.votesession.domain.dto.ParameterResponse;
import br.com.isaquebrb.votesession.service.ParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("parameter")
public class ParameterController {

    private final ParameterService service;

    @PatchMapping
    public ResponseEntity<ParameterResponse> updateParam(@Valid @RequestBody ParameterRequest request) {
        return ResponseEntity.ok(service.update(request));
    }
}
