package hu.bgachip.voting.domain.entity;

import hu.bgachip.voting.domain.enums.ProcedureType;
import hu.bgachip.voting.domain.enums.VotingType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
}