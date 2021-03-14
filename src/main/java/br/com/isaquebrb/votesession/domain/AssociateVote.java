package br.com.isaquebrb.votesession.domain;

import br.com.isaquebrb.votesession.domain.enums.VoteChoice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "associate_vote", uniqueConstraints =
@UniqueConstraint(columnNames = {"associate_id", "session_id"}, name = "associate_session_uk"))
@AllArgsConstructor
@NoArgsConstructor
public class AssociateVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Getter
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
