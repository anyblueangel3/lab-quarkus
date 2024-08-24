package api.dto;

import java.util.Optional;

public class Election(String id, List<Candidate> candidates) {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Public record Candidates(String id,
                             Optional<String> photo,
                             String fullName,
                             String email,
                             Optional<String> phone,
                             Optional<String> jobTitle,
                             Integer votes) {

    }
}
