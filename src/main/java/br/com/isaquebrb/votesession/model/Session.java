package br.com.isaquebrb.votesession.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "session")
    private Topic topic;

    private LocalDateTime startDate = LocalDateTime.now();

    private LocalDateTime endDate;

    @OneToMany(mappedBy = "session")
    private Set<AssociateVote> associateVoteList;
}
