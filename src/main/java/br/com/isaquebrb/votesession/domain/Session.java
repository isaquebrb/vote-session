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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @OneToOne(optional = false)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(name = "start_date")
    private LocalDateTime startDate = LocalDateTime.now();

    @Setter
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "session")
    private Set<AssociateVote> associateVoteList;
}
