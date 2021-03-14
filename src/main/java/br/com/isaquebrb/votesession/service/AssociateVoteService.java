package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.domain.Associate;
import br.com.isaquebrb.votesession.domain.AssociateVote;
import br.com.isaquebrb.votesession.domain.Session;
import br.com.isaquebrb.votesession.domain.dto.VotingRequest;
import br.com.isaquebrb.votesession.domain.enums.VoteChoice;
import br.com.isaquebrb.votesession.exception.EntityNotFoundException;
import br.com.isaquebrb.votesession.repository.AssociateVoteRepository;
import br.com.isaquebrb.votesession.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssociateVoteService {

    private final AssociateVoteRepository repository;
    private final AssociateService associateService;
    private final SessionService sessionService;

    @Async
    public void vote(VotingRequest request) {

        Associate associate = getAssociate(request.getDocument());
        Session session = getSession(request.getSessionId());

        if (associate == null || session == null || !isSessionOpen(session)) {
            return;
        }

        VoteChoice choice = VoteChoice.getChoice(StringUtils.normalize(request.getVoteChoice()).toUpperCase());
        saveVote(associate, session, choice);
    }

    private boolean isSessionOpen(Session session) {
        if (session.getEndDate() != null) {
            String msg = "A sessao id " + session.getId() + "ja esta encerrada.";
            log.warn("Method isSessionOpen - " + msg);
            return false;
        }
        return true;
    }

    private void saveVote(Associate associate, Session session, VoteChoice choice) {
        AssociateVote vote = AssociateVote.builder()
                .associate(associate)
                .voteChoice(choice)
                .session(session)
                .build();
        try {
            repository.save(vote);
        } catch (DataIntegrityViolationException e) {
            String msg = "Method saveVote - O CPF " + StringUtils.hideDocument(associate.getDocument())
                    + " ja votou na sessao id " + session.getId();
            log.error(msg, e);
        }
    }

    private Associate getAssociate(String document) {
        try {
            return associateService.findByDocument(document);
        } catch (EntityNotFoundException e) {
            //no need to throw exception in these async process
            return null;
        }
    }

    private Session getSession(Long sessionId) {
        try {
            //todo cacheable
            return sessionService.findById(sessionId);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }
}
