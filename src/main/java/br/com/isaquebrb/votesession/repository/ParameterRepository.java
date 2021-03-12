package br.com.isaquebrb.votesession.repository;

import br.com.isaquebrb.votesession.domain.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    Optional<Parameter> findByName(String name);
}
