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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private TopicStatus status = TopicStatus.OPENED;

    @OneToOne
    private Session session;

    public TopicResponse toDto() {
        return new TopicResponse(id, name, description, status.name());
    }
}
