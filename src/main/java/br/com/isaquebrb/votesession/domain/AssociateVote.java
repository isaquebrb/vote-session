package br.com.isaquebrb.votesession.model;

import br.com.isaquebrb.votesession.model.enums.Vote;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "associate_vote")
public class AssociateVote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Vote vote;

    @ManyToOne
    private Associate associate;

    private LocalDateTime voteDate = LocalDateTime.now();

    @ManyToOne
    private Session session;
}
