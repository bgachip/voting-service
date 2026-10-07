package hu.bgachip.voting.generator;

import hu.bgachip.voting.repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class VotingIdGenerator {

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final int ID_LENGTH = 6;

    private final VotingRepository votingRepository;

    public String generate() {
        String votingId;

        do {
            votingId = generateRandomId();
        } while (votingRepository.existsByVotingId(votingId));

        return votingId;
    }

    private String generateRandomId() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        return IntStream.range(0, ID_LENGTH)
                .mapToObj(i -> String.valueOf(
                        CHARACTERS.charAt(
                                random.nextInt(CHARACTERS.length())
                        )
                ))
                .collect(Collectors.joining());
    }
}