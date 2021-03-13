package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.domain.Associate;
import br.com.isaquebrb.votesession.domain.dto.AssociateRequest;
import br.com.isaquebrb.votesession.repository.AssociateRepository;
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
            log.error("O CPF final {} ja esta cadastrado.", dto.getDocument()
                    .substring(dto.getDocument().length() - 5));
            throw new IllegalArgumentException("Erro");
        }
    }

}
