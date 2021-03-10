package br.com.isaquebrb.votesession.model;

import br.com.isaquebrb.votesession.model.enums.TopicStatus;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "topic")
@NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Topic name must not be null or blank")
    private String name;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private TopicStatus status = TopicStatus.UNDEFINED;

    private Session session;
}
