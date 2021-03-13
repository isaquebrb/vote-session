package br.com.isaquebrb.votesession.domain;

import br.com.isaquebrb.votesession.domain.dto.TopicResponse;
import br.com.isaquebrb.votesession.domain.enums.TopicStatus;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "topic")
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private TopicStatus status = TopicStatus.OPENED;

    @OneToOne(mappedBy = "topic")
    private Session session;

    public TopicResponse toDto() {
        return new TopicResponse(id, name, description, status.name());
    }
}
