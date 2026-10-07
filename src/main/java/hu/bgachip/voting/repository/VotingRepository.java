package hu.bgachip.voting.repository;

import hu.bgachip.voting.domain.entity.Voting;
import hu.bgachip.voting.domain.enums.VotingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface VotingRepository extends JpaRepository<Voting, Long> {

    boolean existsByVotingId(String votingId);

    boolean existsByDateTime(Instant dateTime);

    Optional<Voting> findByVotingId(String votingId);

    Optional<Voting> findFirstByTypeAndDateTimeBeforeOrderByDateTimeDesc(
            VotingType type,
            Instant dateTime
    );
}