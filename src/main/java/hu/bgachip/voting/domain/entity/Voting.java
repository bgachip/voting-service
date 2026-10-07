package hu.bgachip.voting.domain.entity;

import hu.bgachip.voting.domain.enums.ProcedureType;
import hu.bgachip.voting.domain.enums.VoteType;
import hu.bgachip.voting.domain.enums.VotingType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(
        name = "votings",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_voting_public_id", columnNames = "voting_id"),
                @UniqueConstraint(name = "uk_voting_date_time", columnNames = "date_time")
        }
)
public class Voting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voting_id", nullable = false, updatable = false)
    private String votingId;

    @Column(name = "date_time", nullable = false)
    private Instant dateTime;

    @Column(nullable = false)
    private String subject;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VotingType type;

    @Enumerated(EnumType.STRING)
    private ProcedureType procedure;

    @Column(nullable = false)
    private String president;

    @OneToMany(
            mappedBy = "voting",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Vote> votes = new ArrayList<>();

    public void addVote(String representative, VoteType voteType) {
        votes.add(new Vote(this, representative, voteType));
    }

    public Voting(
            String votingId,
            Instant dateTime,
            String subject,
            VotingType type,
            ProcedureType procedure,
            String president
    ) {
        this.votingId = votingId;
        this.dateTime = dateTime;
        this.subject = subject;
        this.type = type;
        this.procedure = procedure;
        this.president = president;
    }
}