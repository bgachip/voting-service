package hu.bgachip.voting.domain.entity;

import hu.bgachip.voting.domain.enums.VoteType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    Vote(Voting voting, String representative, VoteType vote) {
        this.voting = voting;
        this.representative = representative;
        this.vote = vote;
    }

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