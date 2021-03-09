package br.com.isaquebrb.votesession.model;

import br.com.isaquebrb.votesession.model.enums.Vote;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "member_vote")
@NoArgsConstructor
public class MemberVote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Vote vote;

    private Member member;

    private LocalDateTime voteDate;

    private Session session;
}
