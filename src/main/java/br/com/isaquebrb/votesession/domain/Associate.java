package br.com.isaquebrb.votesession.domain;

import br.com.isaquebrb.votesession.domain.dto.AssociateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@Table(name = "associate", uniqueConstraints =
@UniqueConstraint(columnNames = "document", name = "document_uk"))
@NoArgsConstructor
@AllArgsConstructor
public class Associate {

    public Associate(String document, String name) {
        this.document = document;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "document")
    private String document;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "associate")
    private Set<AssociateVote> associateVoteList;

    public AssociateRequest toDto() {
        return new AssociateRequest(document, name);
    }
}
