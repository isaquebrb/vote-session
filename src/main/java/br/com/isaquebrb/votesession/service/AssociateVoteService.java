package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.domain.Associate;
import br.com.isaquebrb.votesession.domain.AssociateVote;
import br.com.isaquebrb.votesession.domain.Session;
import br.com.isaquebrb.votesession.domain.dto.VotingRequest;
import br.com.isaquebrb.votesession.domain.enums.VoteChoice;
import br.com.isaquebrb.votesession.repository.AssociateRepository;
import br.com.isaquebrb.votesession.repository.AssociateVoteRepository;
import br.com.isaquebrb.votesession.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssociateVoteService {

    private final AssociateVoteRepository repository;
    private final AssociateRepository associateRepository;
    private final SessionRepository sessionRepository;

    @Async
    public void vote(VotingRequest request) {

        Optional<Associate> associate = getAssociate(request.getDocument());
        Optional<Session> session = getSession(request.getSessionId());

        if (associate.isEmpty() || session.isEmpty()) {
            return;
        }

        AssociateVote vote = AssociateVote.builder()
                .associate(associate.get())
                .voteChoice(VoteChoice.valueOf(request.getVoteChoice()))
                .session(session.get())
                .build();
        try {
            repository.save(vote);
        } catch (DataIntegrityViolationException e) {
            String doc = associate.get().getDocument();
            log.error("O CPF final {} ja votou na sessao {}.", doc.substring(doc.length() - 5), session.get().getId());
        }
    }

    private Optional<Associate> getAssociate(String document) {
        Optional<Associate> associate = associateRepository.findByDocument(document);

        if (associate.isEmpty()) {
            log.warn("O CPF final '{}' nao esta cadastrado.", document.substring(document.length() - 5));
        }
        return associate;
    }

    private Optional<Session> getSession(Long sessionId) {
        //todo cacheable
        Optional<Session> session = sessionRepository.findById(sessionId);

        if (session.isEmpty()) {
            log.warn("A sessao id {} nao foi localizada.", sessionId);
        } else {
            if (session.get().getEndDate() != null) {
                log.warn("A sessao id={} ja esta encerrada.", sessionId);
                return Optional.empty();
            }
        }
        return session;
    }
}
