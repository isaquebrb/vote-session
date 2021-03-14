package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.domain.Parameter;
import br.com.isaquebrb.votesession.domain.enums.parameters.IntegerParameter;
import br.com.isaquebrb.votesession.repository.ParameterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParameterService {

    private final ParameterRepository repository;

    public Integer getSessionDurationMinutes() {
        return getIntParameter(IntegerParameter.SESSION_DURATION_MINUTES);
    }

    private Integer getIntParameter(IntegerParameter parameter) {
        return repository.findByName(parameter.name()).map(Parameter::getIntegerValue)
                .orElse(parameter.getDefaultValue());
    }
    //todo paramter update
}
