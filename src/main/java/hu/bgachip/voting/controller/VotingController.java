package hu.bgachip.voting.controller;

import hu.bgachip.voting.dto.request.CreateVotingRequest;
import hu.bgachip.voting.dto.response.CreateVotingResponse;
import hu.bgachip.voting.dto.response.VoteResponse;
import hu.bgachip.voting.service.VotingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/szavazasok")
@RequiredArgsConstructor
public class VotingController {

    private final VotingService votingService;

    @GetMapping("/szavazat")
    public ResponseEntity<VoteResponse> getVote(
            @RequestParam("szavazas") String votingId,
            @RequestParam("kepviselo") String representative
    ) {
        return ResponseEntity.ok(
                votingService.getVote(votingId, representative)
        );
    }

    @PostMapping("/szavazas")
    public ResponseEntity<CreateVotingResponse> createVoting(
            @Valid @RequestBody CreateVotingRequest request
    ) {
        CreateVotingResponse response = votingService.createVoting(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}