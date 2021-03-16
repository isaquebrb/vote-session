package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.config.CacheConfig;
import br.com.isaquebrb.votesession.domain.Session;
import br.com.isaquebrb.votesession.domain.Topic;
import br.com.isaquebrb.votesession.domain.dto.SessionResponse;
import br.com.isaquebrb.votesession.domain.enums.TopicStatus;
import br.com.isaquebrb.votesession.exception.BusinessException;
import br.com.isaquebrb.votesession.exception.DatabaseException;
import br.com.isaquebrb.votesession.exception.EntityNotFoundException;
import br.com.isaquebrb.votesession.repository.SessionRepository;
import br.com.isaquebrb.votesession.task.SessionRunnable;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository repository;
    private final TopicService topicService;
    private final TaskScheduler taskScheduler;
    private final ParameterService parameterService;


    public SessionResponse startSession(Long topicId) {
        Topic topic = topicService.findById(topicId);

        if (topic.getStatus().equals(TopicStatus.CLOSED)) {
            String msg = "A pauta " + topic.getName() + " ja esta encerrada.";
            log.warn("Method startSession - " + msg);
            throw new BusinessException(msg);
        }

        Session session = saveSession(topic);
        runSession(session);

        return session.toDto();
    }

    @Cacheable(value = CacheConfig.SESSION_CACHE, key = "#id")
    public Session findById(Long id) {
        log.info("Find session with id {}", id);
        return repository.findById(id).orElseThrow(() -> {
            String msg = "A sessao id " + id + " nao foi localizada.";
            log.error("Method findById - " + msg);
            throw new EntityNotFoundException(msg);
        });
    }

    public void closeSession(Long sessionId) {
        Optional<Session> optSession = repository.findSessionVotesById(sessionId);
        if (optSession.isEmpty()) {
            log.error("Method closeSession - Sessao id {} nao foi localizada.", sessionId);
            return;
        }

        optSession.get().setEndDate(LocalDateTime.now());
        repository.save(optSession.get());
        log.info("Method closeSession - Sessao de votacao encerrada. Sessao id {}", sessionId);

        topicService.saveVotingResult(optSession.get().getTopic());
    }

    private void runSession(Session session) {
        Integer durationMinutes = parameterService.getSessionDurationMinutes();
        Instant endDate = Instant.now().plusSeconds(durationMinutes * 60);
        taskScheduler.schedule(new SessionRunnable(session.getId(), this), endDate);
        log.info("Method runSession - A sessao da pauta '{}' esta aberta para votacao. Tempo de duracao: {} minutos",
                session.getTopic().getName(), durationMinutes);
    }

    private Session saveSession(Topic topic) {
        try {
            Session session = new Session();
            session.setTopic(topic);
            return repository.save(session);
        } catch (DataIntegrityViolationException e) {
            String msg = "Erro ao salvar a sessao, a pauta " + topic.getName() + " ja possui uma sessao vinculada.";
            log.error("Method saveSession - " + msg, e);
            throw new DatabaseException(msg);
        }
    }
}
