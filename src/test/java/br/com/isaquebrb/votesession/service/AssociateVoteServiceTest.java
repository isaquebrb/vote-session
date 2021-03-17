package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.domain.Associate;
import br.com.isaquebrb.votesession.domain.AssociateVote;
import br.com.isaquebrb.votesession.domain.Session;
import br.com.isaquebrb.votesession.domain.dto.VotingRequest;
import br.com.isaquebrb.votesession.domain.enums.VoteChoice;
import br.com.isaquebrb.votesession.exception.EntityNotFoundException;
import br.com.isaquebrb.votesession.exception.ErrorMessage;
import br.com.isaquebrb.votesession.integration.UserInfoService;
import br.com.isaquebrb.votesession.repository.AssociateVoteRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssociateVoteServiceTest {

    @InjectMocks
    AssociateVoteService service;
    @Mock
    AssociateVoteRepository repository;
    @Mock
    AssociateService associateService;
    @Mock
    SessionService sessionService;
    @Mock
    UserInfoService userInfoService;
    @Captor
    ArgumentCaptor<AssociateVote> voteCaptor;


    private static final String DOCUMENT = "12345678910";
    private static Associate associate;
    private static Session session;

    @BeforeAll
    static void setUp() {
        associate = new Associate(DOCUMENT, "Associate random");
        session = new Session(1L, null, LocalDateTime.now(), null, null);
    }

    @Test
    void whenVote_withYes_thenSaveYesVote() {
        VotingRequest request = new VotingRequest(DOCUMENT, "SIM", 1L);

        when(associateService.findByDocument(anyString())).thenReturn(associate);
        when(sessionService.findById(anyLong())).thenReturn(session);
        when(userInfoService.isAbleToVote(anyString())).thenReturn(true);

        service.vote(request);

        verify(repository).save(voteCaptor.capture());
        AssociateVote value = voteCaptor.getValue();

        assertThat(value.getVoteChoice()).isEqualTo(VoteChoice.YES);
    }

    @Test
    void whenVote_withNo_thenSaveNoVote() {
        VotingRequest request = new VotingRequest(DOCUMENT, "NÃO", 1L);

        when(associateService.findByDocument(anyString())).thenReturn(associate);
        when(sessionService.findById(anyLong())).thenReturn(session);
        when(userInfoService.isAbleToVote(anyString())).thenReturn(true);

        service.vote(request);

        verify(repository).save(voteCaptor.capture());
        AssociateVote value = voteCaptor.getValue();

        assertThat(value.getVoteChoice()).isEqualTo(VoteChoice.NO);
    }

    @Test
    void whenVote_withoutExistingAssociate_thenDoNothing() {
        VotingRequest request = new VotingRequest(DOCUMENT, "NÃO", 1L);

        when(associateService.findByDocument(anyString()))
                .thenThrow(new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND.getMessage()));

        service.vote(request);

        verify(repository, times(0)).save(any(AssociateVote.class));
    }

    @Test
    void whenVote_withoutExistingSession_thenDoNothing() {
        VotingRequest request = new VotingRequest(DOCUMENT, "NÃO", 1L);

        when(associateService.findByDocument(anyString())).thenReturn(associate);

        when(sessionService.findById(anyLong()))
                .thenThrow(new EntityNotFoundException(ErrorMessage.ENTITY_NOT_FOUND.getMessage()));

        service.vote(request);

        verify(repository, times(0)).save(any(AssociateVote.class));
    }

    @Test
    void whenVote_withSessionEnded_thenDoNothing() {
        VotingRequest request = new VotingRequest(DOCUMENT, "NÃO", 1L);

        when(associateService.findByDocument(anyString())).thenReturn(associate);

        Session newSession = new Session();
        newSession.setEndDate(LocalDateTime.now());
        when(sessionService.findById(anyLong())).thenReturn(newSession);

        service.vote(request);

        verify(repository, times(0)).save(any(AssociateVote.class));
    }

    @Test
    void whenVote_AndIsNotAble_thenDoNothing() {
        VotingRequest request = new VotingRequest(DOCUMENT, "NÃO", 1L);

        when(associateService.findByDocument(anyString())).thenReturn(associate);
        when(sessionService.findById(anyLong())).thenReturn(session);
        when(userInfoService.isAbleToVote(anyString())).thenReturn(false);

        service.vote(request);

        verify(repository, times(0)).save(any(AssociateVote.class));
    }
}
