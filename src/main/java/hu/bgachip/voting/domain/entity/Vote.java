package hu.bgachip.voting.domain.entity;

import hu.bgachip.voting.domain.enums.VoteType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "votes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_vote_voting_representative",
                        columnNames = {"voting_id", "representative"}
                )
        }
)
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "voting_id", nullable = false)
    private Voting voting;

    @Column(nullable = false)
    private String representative;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoteType vote;

}