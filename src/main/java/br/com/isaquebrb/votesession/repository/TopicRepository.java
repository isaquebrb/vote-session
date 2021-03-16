package br.com.isaquebrb.votesession.repository;

import br.com.isaquebrb.votesession.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
}
