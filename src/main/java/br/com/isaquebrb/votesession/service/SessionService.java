package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.domain.Session;
import br.com.isaquebrb.votesession.domain.Topic;
import br.com.isaquebrb.votesession.domain.enums.TopicStatus;
import br.com.isaquebrb.votesession.repository.SessionRepository;
import br.com.isaquebrb.votesession.repository.TopicRepository;
import br.com.isaquebrb.votesession.task.SessionRunnable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository repository;
    private final TopicRepository topicRepository;
    private final TaskScheduler taskScheduler;
    private final ParameterService parameterService;


    public void startSession(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(IllegalArgumentException::new);

        if (topic.getStatus().equals(TopicStatus.CLOSED)) {
            log.warn("A pauta '{}' ja esta encerrada.", topic.getName());
            return;
        }

        Session session = new Session();
        session.setTopic(topic);
        repository.save(session);

        topic.setSession(session);
        runSession(topic);
    }

    public void closeSession(Topic topic) {
        topic.setStatus(TopicStatus.CLOSED);
        topicRepository.save(topic);

        Session session = topic.getSession();
        session.setEndDate(LocalDateTime.now());
        repository.save(session);

        log.info("Sessao de votacao finalizada. Pauta '{}' encerrada.", topic.getName());
    }

    private void runSession(Topic topic) {
        Integer durationMinutes = parameterService.getSessionDurationMinutes();
        Instant endDate = Instant.now().plusSeconds(durationMinutes * 60);
        taskScheduler.schedule(new SessionRunnable(topic, this), endDate);
        log.info("A sessao da pauta '{}' esta aberta para votacao. Tempo de duracao: {} minutos", topic.getName(), durationMinutes);
    }
}
