package br.com.isaquebrb.votesession.repository;

import br.com.isaquebrb.votesession.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("SELECT s FROM Session s LEFT JOIN FETCH s.associateVotes where s.id = :id")
    Optional<Session> findSessionVotesById(@Param("id") Long id);
}
