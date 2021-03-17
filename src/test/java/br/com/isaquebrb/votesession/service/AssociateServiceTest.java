package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.domain.Associate;
import br.com.isaquebrb.votesession.domain.AssociateVote;
import br.com.isaquebrb.votesession.domain.dto.AssociateRequest;
import br.com.isaquebrb.votesession.exception.DatabaseException;
import br.com.isaquebrb.votesession.repository.AssociateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssociateServiceTest {

    @InjectMocks
    AssociateService service;

    @Mock
    AssociateRepository repository;

    private static final String DOCUMENT = "12345678910";
    private static final String NAME = "Nome associado";

    @Test
    void whenValidAssociate_thenReturnDto() {
        AssociateRequest request = new AssociateRequest(DOCUMENT, NAME);
        Associate associate = new Associate(1L, DOCUMENT, NAME, Collections.singleton(new AssociateVote()));

        when(repository.save(any(Associate.class))).thenReturn(associate);

        AssociateRequest response = service.addAssociate(request);

        assertThat(request.getDocument()).isEqualTo(response.getDocument());
        assertThat(request.getName()).isEqualTo(response.getName());
    }

    @Test
    void whenDuplicateAssociate_thenThrowsDatabaseException() {
        AssociateRequest request = new AssociateRequest(DOCUMENT, NAME);

        when(repository.save(any(Associate.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicated document"));

        assertThrows(DatabaseException.class, () -> service.addAssociate(request));
    }
}
