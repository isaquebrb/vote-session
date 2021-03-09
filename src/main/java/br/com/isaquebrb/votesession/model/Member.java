package br.com.isaquebrb.votesession.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "member")
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Member document (cpf) must not be null or blank")
    private String document;

    @NotBlank(message = "Member name must not be null or blank")
    private String name;

    @Email(message = "Member e-mail is invalid")
    private String email;

    private Boolean active;

    @OneToMany(mappedBy = "member")
    private Set<MemberVote> memberVoteList;
}
