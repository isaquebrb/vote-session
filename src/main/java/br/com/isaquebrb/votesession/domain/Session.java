package br.com.isaquebrb.votesession.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Entity
@Table(name = "session")
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "session")
    private Topic topic;

    private LocalDateTime startDate = LocalDateTime.now();

    @Setter
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "session")
    private Set<AssociateVote> associateVoteList;
}
