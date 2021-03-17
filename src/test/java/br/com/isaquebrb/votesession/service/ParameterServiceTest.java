package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.domain.Parameter;
import br.com.isaquebrb.votesession.domain.dto.ParameterRequest;
import br.com.isaquebrb.votesession.domain.enums.parameters.IntegerParameter;
import br.com.isaquebrb.votesession.exception.DatabaseException;
import br.com.isaquebrb.votesession.repository.ParameterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParameterServiceTest {

    @InjectMocks
    ParameterService service;

    @Mock
    ParameterRepository repository;

    @Captor
    ArgumentCaptor<Parameter> parameterCaptor;

    @Test
    void whenGetParameter_withNonexistentParameter_thenReturnItsDefaultValue() {
        Integer durationMinutes = service.getSessionDurationMinutes();
        assertThat(durationMinutes).isEqualTo(IntegerParameter.SESSION_DURATION_MINUTES.getDefaultValue());
    }

    @Test
    void whenUpdate_withException_thenReturnDatabaseException() {
        ParameterRequest request = new ParameterRequest("SESSION_DURATION_MINUTES", 2);

        assertThrows(DatabaseException.class, () -> service.update(request));
    }

    @Test
    void whenUpdate_thenChangeParameterValue() {
        Parameter parameter = new Parameter(1L, "SESSION_DURATION_MINUTES", 5, null);
        ParameterRequest request = new ParameterRequest("SESSION_DURATION_MINUTES", 1);

        when(repository.findByName(anyString())).thenReturn(Optional.of(parameter));
        when(repository.save(any(Parameter.class))).thenReturn(parameter);

        service.update(request);

        verify(repository).save(parameterCaptor.capture());
        Parameter newParameter = parameterCaptor.getValue();

        assertThat(newParameter.getIntegerValue()).isEqualTo(request.getValue());
    }

    @Test
    void whenUpdate_withNewParameter_thenSaveNewParameter() {
        Parameter parameter = new Parameter(1L, "SESSION_DURATION_MINUTES", 5, null);
        ParameterRequest request = new ParameterRequest("NEW_PARAMETER_NAME", 1);

        when(repository.findByName(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(Parameter.class))).thenReturn(parameter);

        service.update(request);

        verify(repository).save(parameterCaptor.capture());
        Parameter newParameter = parameterCaptor.getValue();

        assertThat(newParameter.getIntegerValue()).isEqualTo(request.getValue());
        assertThat(newParameter.getName()).isEqualTo(request.getName());
    }
}
