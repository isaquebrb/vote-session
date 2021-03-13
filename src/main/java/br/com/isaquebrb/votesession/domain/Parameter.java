package br.com.isaquebrb.votesession.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "parameter")
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "integer_value")
    private Integer integerValue;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}