package br.com.isaquebrb.votesession.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@Entity
@Table(name = "associate", uniqueConstraints =
@UniqueConstraint(columnNames = "document", name = "document_uk"))
@NoArgsConstructor
@AllArgsConstructor
public class Associate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String document;

    private String name;

    @OneToMany(mappedBy = "associate")
    private Set<AssociateVote> associateVoteList;
}
