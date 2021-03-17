package br.com.isaquebrb.votesession.domain;

import br.com.isaquebrb.votesession.domain.dto.ParameterResponse;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "parameter")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @Column(name = "name")
    private String name;

    @Setter
    @Column(name = "integer_value")
    private Integer integerValue;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ParameterResponse toDto() {
        return new ParameterResponse(name, integerValue);
    }
}