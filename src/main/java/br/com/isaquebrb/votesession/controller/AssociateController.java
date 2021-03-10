package br.com.isaquebrb.votesession.controller;

import br.com.isaquebrb.votesession.domain.dto.AssociateDto;
import br.com.isaquebrb.votesession.service.AssociateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/associate")
public class AssociateController {

    private final AssociateService service;

    @PostMapping("/add")
    public ResponseEntity<AssociateDto> addAssociate(@Valid @RequestBody AssociateDto associate) {
        return ResponseEntity.ok(service.addAssociate(associate));
    }
}
