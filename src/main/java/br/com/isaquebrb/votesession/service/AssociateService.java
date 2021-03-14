package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.domain.Associate;
import br.com.isaquebrb.votesession.domain.dto.AssociateRequest;
import br.com.isaquebrb.votesession.exception.DatabaseException;
import br.com.isaquebrb.votesession.exception.EntityNotFoundException;
import br.com.isaquebrb.votesession.repository.AssociateRepository;
import br.com.isaquebrb.votesession.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssociateService {

    private final AssociateRepository repository;

    public AssociateRequest addAssociate(AssociateRequest dto) {
        try {
            Associate associate = dto.toEntity();
            return repository.save(associate).toDto();
        } catch (DataIntegrityViolationException e) {
            String msg = "O CPF " + StringUtils.hideDocument(dto.getDocument()) + " ja esta cadastrado.";
            log.error("Method addAssociate - " + msg);
            throw new DatabaseException(msg);
        }
    }

    public Associate findByDocument(String document) {
        return repository.findByDocument(document).orElseThrow(() -> {
            String msg = "O CPF " + StringUtils.hideDocument(document) + " nao foi localizado.";
            log.error("Method findByDocument - " + msg);
            throw new EntityNotFoundException(msg);
        });
    }
}
