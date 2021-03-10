package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.domain.Associate;
import br.com.isaquebrb.votesession.domain.dto.AssociateDto;
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

    public AssociateDto addAssociate(AssociateDto dto) {
        try {
            Associate associate = dto.toEntity();
            associate = repository.save(associate);
            return new AssociateDto(associate);
        } catch (DataIntegrityViolationException e) {
            log.error("Documento {} já existe.", dto.getDocument(), e);
            throw new IllegalArgumentException("Erro");
        }
    }

}
