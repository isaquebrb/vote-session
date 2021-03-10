package br.com.isaquebrb.votesession.repository;

import br.com.isaquebrb.votesession.domain.Associate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociateRepository extends JpaRepository<Associate, Long> {

}
