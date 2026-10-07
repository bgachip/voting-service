package hu.bgachip.voting.repository;

import hu.bgachip.voting.domain.entity.Voting;
import hu.bgachip.voting.domain.enums.VotingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface VotingRepository extends JpaRepository<Voting, Long> {

    boolean existsByVotingId(String votingId);

    boolean existsByDateTime(Instant dateTime);

    Optional<Voting> findByVotingId(String votingId);

    Optional<Voting> findFirstByTypeAndDateTimeBeforeOrderByDateTimeDesc(
            VotingType type,
            Instant dateTime
    );

    @Query("""
        SELECT v
        FROM Voting v
        WHERE v.dateTime >= :from
          AND v.dateTime < :to
        ORDER BY v.dateTime
        """)
    List<Voting> findAllByDateRange(
            @Param("from") Instant from,
            @Param("to") Instant to
    );
}