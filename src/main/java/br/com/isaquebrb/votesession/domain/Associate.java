package br.com.isaquebrb.votesession.domain;

import br.com.isaquebrb.votesession.domain.dto.AssociateRequest;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@Entity
@Table(name = "associate", uniqueConstraints =
@UniqueConstraint(columnNames = "document", name = "document_uk"))
public class Associate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String document;

    private String name;

    @OneToMany(mappedBy = "associate")
    private Set<AssociateVote> associateVoteList;

    public AssociateRequest toDto() {
        return new AssociateRequest(document, name);
    }
}
