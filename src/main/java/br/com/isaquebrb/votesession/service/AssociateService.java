package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.model.Associate;
import br.com.isaquebrb.votesession.model.dto.AssociateDto;
import br.com.isaquebrb.votesession.repository.AssociateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssociateService {

    private final AssociateRepository repository;

    public AssociateDto addAssociate(AssociateDto dto) {
        try {
            Associate associate = dto.toEntity();
            associate = repository.save(associate);
            return new AssociateDto(associate);
        } catch (Exception e) {
            //todo throw exception
            return null;
        }
    }

}
