package br.com.isaquebrb.votesession.controller;

import br.com.isaquebrb.votesession.domain.dto.AssociateRequest;
import br.com.isaquebrb.votesession.service.AssociateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/associate")
@RequiredArgsConstructor
public class AssociateController {

    private final AssociateService service;

    @PostMapping("/new")
    public ResponseEntity<AssociateRequest> addAssociate(@Valid @RequestBody AssociateRequest associate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addAssociate(associate));
    }
}
