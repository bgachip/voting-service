package hu.bgachip.voting.controller;

import hu.bgachip.voting.dto.request.CreateVotingRequest;
import hu.bgachip.voting.dto.response.CreateVotingResponse;
import hu.bgachip.voting.dto.response.DailyVotingResponse;
import hu.bgachip.voting.dto.response.VoteResponse;
import hu.bgachip.voting.dto.response.VotingResultResponse;
import hu.bgachip.voting.service.VotingResultService;
import hu.bgachip.voting.service.VotingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/szavazasok")
@RequiredArgsConstructor
public class VotingController {

    private final VotingService votingService;
    private final VotingResultService votingResultService;

    @GetMapping("/eredmeny")
    public ResponseEntity<VotingResultResponse> getResult(
            @RequestParam("szavazas") String votingId
    ) {
        return ResponseEntity.ok(
                votingResultService.getResult(votingId)
        );
    }

    @GetMapping("/szavazat")
    public ResponseEntity<VoteResponse> getVote(
            @RequestParam("szavazas") String votingId,
            @RequestParam("kepviselo") String representative
    ) {
        return ResponseEntity.ok(
                votingService.getVote(votingId, representative)
        );
    }

    @GetMapping("/napi-szavazasok")
    public ResponseEntity<DailyVotingResponse> getDailyVotings(
            @RequestParam("datum")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        return ResponseEntity.ok(
                votingService.getDailyVotings(date)
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