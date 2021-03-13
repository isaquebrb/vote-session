package br.com.isaquebrb.votesession.domain;

import br.com.isaquebrb.votesession.domain.enums.VoteChoice;
import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "associate_vote", uniqueConstraints =
@UniqueConstraint(columnNames = {"associate_id", "session_id"}, name = "associate_session_uk"))
public class AssociateVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "vote_choice")
    private VoteChoice voteChoice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "associate_id")
    private Associate associate;

    @Builder.Default
    @Column(name = "vote_date")
    private LocalDateTime voteDate = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "session_id")
    private Session session;
}
