package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.domain.Session;
import br.com.isaquebrb.votesession.domain.Topic;
import br.com.isaquebrb.votesession.domain.enums.TopicStatus;
import br.com.isaquebrb.votesession.repository.SessionRepository;
import br.com.isaquebrb.votesession.repository.TopicRepository;
import br.com.isaquebrb.votesession.task.SessionRunnable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
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
        Session session = new Session();

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(IllegalArgumentException::new);

        if (topic.getStatus().equals(TopicStatus.CLOSED)) {
            log.warn("A pauta '{}' ja foi encerrada.", topic.getName());
            return;
        }

        topic.setSession(session);
        runSession(topic);
    }

    public void closeSession(Topic topic) {
        Session session = topic.getSession();
        session.setEndDate(LocalDateTime.now());
        repository.save(session);

        topic.setStatus(TopicStatus.CLOSED);
        topicRepository.save(topic);
    }

    private void runSession(Topic topic) {
        Integer durationMinutes = parameterService.getSessionDurationMinutes();
        Instant endDate = Instant.now().plusSeconds(durationMinutes * 60);
        taskScheduler.schedule(new SessionRunnable(topic, this), endDate);
    }
}
