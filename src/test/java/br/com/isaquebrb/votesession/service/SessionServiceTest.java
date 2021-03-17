package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.domain.Session;
import br.com.isaquebrb.votesession.domain.Topic;
import br.com.isaquebrb.votesession.domain.dto.SessionResponse;
import br.com.isaquebrb.votesession.domain.enums.TopicStatus;
import br.com.isaquebrb.votesession.exception.BusinessException;
import br.com.isaquebrb.votesession.exception.DatabaseException;
import br.com.isaquebrb.votesession.repository.SessionRepository;
import br.com.isaquebrb.votesession.utils.DateUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.TaskScheduler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @InjectMocks
    SessionService service;
    @Mock
    SessionRepository repository;
    @Mock
    TopicService topicService;
    @Mock
    TaskScheduler taskScheduler;
    @Mock
    ParameterService parameterService;
    @Captor
    ArgumentCaptor<Session> sessionCaptor;

    private static Topic topic;
    private static Session session;

    @BeforeAll
    static void setUp() {
        topic = new Topic(1L, "Pauta XY", "Descricao da Pauta", TopicStatus.OPENED, null, null);
        session = new Session(1L, topic, LocalDateTime.now(), null, null);
    }

    @Test
    void whenStartSession_thenReturnDto() {
        when(topicService.findById(anyLong())).thenReturn(topic);
        when(repository.save(any(Session.class))).thenReturn(session);
        when(parameterService.getSessionDurationMinutes()).thenReturn(1);

        SessionResponse response = service.startSession(1L);

        verify(taskScheduler, times(1)).schedule(any(Runnable.class), any(Instant.class));
        verify(repository, times(1)).save(any(Session.class));

        assertThat(response.getTopicName()).isEqualTo(topic.getName());
        assertThat(response.getId()).isEqualTo(session.getId());
        assertThat(response.getStartDate()).isEqualTo(DateUtils.toDateTime(session.getStartDate()));
    }

    @Test
    void whenStartSession_withClosedTopic_thenThrowsBusinessException() {
        Topic newTopic = new Topic(1L, "Pauta XY", "Descricao da Pauta",
                TopicStatus.CLOSED, null, null);

        when(topicService.findById(anyLong())).thenReturn(newTopic);

        assertThrows(BusinessException.class, () -> service.startSession(1L));
    }

    @Test
    void whenStartSession_withDuplicateTopic_thenThrowsBusinessException() {
        when(topicService.findById(anyLong())).thenReturn(topic);
        when(repository.save(any(Session.class))).thenThrow(new DataIntegrityViolationException("Duplicado"));

        assertThrows(DatabaseException.class, () -> service.startSession(1L));
    }

    @Test
    void whenCloseSession_thenSetEndDateAndCallSaveVotingResult() {
        when(repository.findSessionVotesById(anyLong())).thenReturn(Optional.of(session));

        service.closeSession(1L);

        verify(repository).save(sessionCaptor.capture());
        verify(topicService, times(1)).saveVotingResult(any(Topic.class));

        assertThat(sessionCaptor.getValue().getEndDate()).isNotNull();
    }

    @Test
    void whenCloseSession_withNonexistentSession_thenDoNothing() {
        when(repository.findSessionVotesById(anyLong())).thenReturn(Optional.empty());

        service.closeSession(1L);

        verify(repository, times(0)).save(any(Session.class));
        verify(topicService, times(0)).saveVotingResult(any(Topic.class));
    }
}
