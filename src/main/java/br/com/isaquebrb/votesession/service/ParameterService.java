package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.domain.Parameter;
import br.com.isaquebrb.votesession.domain.dto.ParameterRequest;
import br.com.isaquebrb.votesession.domain.dto.ParameterResponse;
import br.com.isaquebrb.votesession.domain.enums.parameters.IntegerParameter;
import br.com.isaquebrb.votesession.exception.DatabaseException;
import br.com.isaquebrb.votesession.repository.ParameterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParameterService {

    private final ParameterRepository repository;

    public Integer getSessionDurationMinutes() {
        return getIntParameter(IntegerParameter.SESSION_DURATION_MINUTES);
    }

    public ParameterResponse update(ParameterRequest request) {
        Parameter parameter = new Parameter();
        Optional<Parameter> optionalParam = repository.findByName(request.getName());

        if (optionalParam.isEmpty()) {
            String msg = "Um novo parametro " + request.getName() + " sera cadastrado.";
            log.warn("Method update - " + msg);
            parameter.setName(request.getName());
        } else {
            parameter = optionalParam.get();
        }

        try {
            if (request.getValue() instanceof Integer) {
                Integer value = (Integer) request.getValue();
                parameter.setIntegerValue(value);
            }
            return repository.save(parameter).toDto();
        } catch (Exception e) {
            String msg = "Erro ao salvar o parametro " + parameter.toString();
            log.error("Method update - " + msg, e);
            throw new DatabaseException(msg);
        }
    }

    private Integer getIntParameter(IntegerParameter parameter) {
        return repository.findByName(parameter.name()).map(Parameter::getIntegerValue)
                .orElse(parameter.getDefaultValue());
    }
}
