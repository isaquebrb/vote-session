package br.com.isaquebrb.votesession.repository;

import br.com.isaquebrb.votesession.domain.AssociateVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociateVoteRepository extends JpaRepository<AssociateVote, Long> {
}
