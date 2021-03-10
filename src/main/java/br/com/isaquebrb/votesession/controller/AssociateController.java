package br.com.isaquebrb.votesession.controller;

import br.com.isaquebrb.votesession.model.Associate;
import br.com.isaquebrb.votesession.model.dto.AssociateDto;
import br.com.isaquebrb.votesession.service.AssociateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/associate")
public class AssociateController {

    private final AssociateService service;

    @PostMapping("/add")
    public ResponseEntity<AssociateDto> addAssociate(@Valid @RequestBody AssociateDto associate){
        return ResponseEntity.ok(service.addAssociate(associate));
    }
}
