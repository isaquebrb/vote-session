package br.com.isaquebrb.votesession.service;

import br.com.isaquebrb.votesession.domain.Topic;
import br.com.isaquebrb.votesession.domain.dto.TopicRequest;
import br.com.isaquebrb.votesession.domain.dto.TopicResponse;
import br.com.isaquebrb.votesession.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository repository;

    public TopicResponse createTopic(TopicRequest dto) {
        Topic topic = dto.toEntity();
        return repository.save(topic).toDto();
    }
}
