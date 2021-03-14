package br.com.isaquebrb.votesession.repository;

import br.com.isaquebrb.votesession.domain.Topic;
import br.com.isaquebrb.votesession.domain.dto.VotingResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    /*@Query("")
    VotingResult countVoting(@Param("topicId") Long topicId, @Param("sessionId") Long sessionId);*/
}
