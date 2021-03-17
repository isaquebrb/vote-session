package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.domain.Topic;
import br.com.isaquebrb.votesession.domain.dto.TopicRequest;
import br.com.isaquebrb.votesession.domain.dto.TopicResponse;
import br.com.isaquebrb.votesession.domain.enums.TopicResult;
import br.com.isaquebrb.votesession.domain.enums.TopicStatus;
import br.com.isaquebrb.votesession.exception.DatabaseException;
import br.com.isaquebrb.votesession.exception.EntityNotFoundException;
import br.com.isaquebrb.votesession.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository repository;

    public TopicResponse createTopic(TopicRequest dto) {
        Topic topic = dto.toEntity();
        return repository.save(topic).toDto();
    }

    public Topic findById(Long id) {
        return repository.findById(id).orElseThrow(() -> {
            String msg = "A pauta " + id + " nao foi localizada.";
            log.error("Method findById - " + msg);
            throw new EntityNotFoundException(msg);
        });
    }

    public Topic save(Topic topic) {
        try {
            return repository.save(topic);
        } catch (Exception e) {
            String msg = "Erro ao salvar a pauta: " + topic.toString();
            log.error("Method save - " + msg);
            throw new DatabaseException(msg);
        }
    }

    public void saveVotingResult(Topic topic) {
        try {
            if (topic.getSession().getYesVotes() > topic.getSession().getNoVotes()) {
                topic.setResult(TopicResult.APPROVED);
            } else {
                topic.setResult(TopicResult.REJECTED);
            }

            topic.setStatus(TopicStatus.CLOSED);
            repository.save(topic);
            log.info("Method saveVotingResult - A pauta {} foi {}.", topic.getName(), topic.getResult().getLabel());
        } catch (Exception e) {
            String msg = "Erro ao salvar a pauta " + topic.getName();
            log.error("Method saveVotingResult - " + msg, e);
        }

    }
}
