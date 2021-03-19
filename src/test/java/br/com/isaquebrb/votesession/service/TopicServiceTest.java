package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.domain.AssociateVote;
import br.com.isaquebrb.votesession.domain.Session;
import br.com.isaquebrb.votesession.domain.Topic;
import br.com.isaquebrb.votesession.domain.dto.TopicRequest;
import br.com.isaquebrb.votesession.domain.dto.TopicResponse;
import br.com.isaquebrb.votesession.domain.enums.TopicResult;
import br.com.isaquebrb.votesession.domain.enums.TopicStatus;
import br.com.isaquebrb.votesession.domain.enums.VoteChoice;
import br.com.isaquebrb.votesession.exception.EntityNotFoundException;
import br.com.isaquebrb.votesession.kafka.KafkaProducer;
import br.com.isaquebrb.votesession.repository.TopicRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {

    @InjectMocks
    TopicService service;

    @Mock
    TopicRepository repository;

    @Mock
    KafkaProducer kafkaProducer;

    @Captor
    ArgumentCaptor<Topic> topicCaptor;

    private static final String NAME = "Nome associado";
    private static final String DESCRIPTION = "12345678910";
    private static Topic topic;

    @BeforeAll
    static void setUp() {
        topic = new Topic(1L, NAME, DESCRIPTION, TopicStatus.OPENED, TopicResult.APPROVED, null);
    }

    @Test
    void whenCreateTopic_thenReturnDto() {
        TopicRequest request = new TopicRequest(NAME, DESCRIPTION);

        when(repository.save(any(Topic.class))).thenReturn(topic);

        TopicResponse response = service.createTopic(request);

        assertThat(request.getName()).isEqualTo(response.getName());
        assertThat(request.getDescription()).isEqualTo(response.getDescription());
        assertThat(topic.getId()).isEqualTo(response.getId());
    }

    @Test
    void whenFindById_withExistingId_thenReturnEntity() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(topic));

        Topic topicFound = service.findById(topic.getId());

        assertThat(topic).isEqualTo(topicFound);
    }

    @Test
    void whenFindById_withNonexistentId_thenThrowsNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void whenSaveVotingResult_withMoreYesVotes_thenCloseAndApprove() {
        Set<AssociateVote> votes = Set.of(
                new AssociateVote(1L, VoteChoice.YES, null, LocalDateTime.now(), null),
                new AssociateVote(2L, VoteChoice.YES, null, LocalDateTime.now(), null),
                new AssociateVote(3L, VoteChoice.NO, null, LocalDateTime.now(), null));

        Session session = new Session(1L, null, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), votes);

        Topic topic = new Topic(1L, NAME, DESCRIPTION, TopicStatus.OPENED, null, null);
        topic.setSession(session);

        service.saveVotingResult(topic);

        verify(kafkaProducer, times(1)).send(any(TopicResponse.class));
        verify(repository).save(topicCaptor.capture());
        Topic value = topicCaptor.getValue();

        assertThat(value.getId()).isEqualTo(topic.getId());
        assertThat(value.getResult()).isEqualTo(TopicResult.APPROVED);
        assertThat(value.getStatus()).isEqualTo(TopicStatus.CLOSED);
    }

    @Test
    void whenSaveVotingResult_withMoreNoVotes_thenCloseAndApprove() {
        Set<AssociateVote> votes = Set.of(
                new AssociateVote(1L, VoteChoice.YES, null, LocalDateTime.now(), null),
                new AssociateVote(2L, VoteChoice.NO, null, LocalDateTime.now(), null),
                new AssociateVote(3L, VoteChoice.NO, null, LocalDateTime.now(), null));

        Session session = new Session(1L, null, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1), votes);

        Topic topic = new Topic(1L, NAME, DESCRIPTION, TopicStatus.OPENED, null, null);
        topic.setSession(session);

        service.saveVotingResult(topic);

        verify(kafkaProducer, times(1)).send(any(TopicResponse.class));
        verify(repository).save(topicCaptor.capture());
        Topic value = topicCaptor.getValue();

        assertThat(value.getId()).isEqualTo(topic.getId());
        assertThat(value.getResult()).isEqualTo(TopicResult.REJECTED);
        assertThat(value.getStatus()).isEqualTo(TopicStatus.CLOSED);
    }

    @Test
    void whenSavingVotesThrowsException_ThenDoNoting() {
        service.saveVotingResult(topic);

        verify(kafkaProducer, times(0)).send(any(TopicResponse.class));
        verify(repository, times(0)).save(any(Topic.class));
    }
}
