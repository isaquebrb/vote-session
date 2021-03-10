package br.com.isaquebrb.votesession.model;

import br.com.isaquebrb.votesession.model.enums.Vote;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class AssociateVote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Vote vote;

    private Associate associate;

    private LocalDateTime voteDate = LocalDateTime.now();

    private Session session;
}
