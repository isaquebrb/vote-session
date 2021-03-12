package br.com.isaquebrb.votesession.repository;

import br.com.isaquebrb.votesession.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}
