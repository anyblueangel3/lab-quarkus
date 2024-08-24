package api;

import api.dto.in.CreateCandidate;
import domain.Candidate;
import domain.CandidateService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class CandidateApiTest {

    @Inject
    CandidateApi candidateApi;

    @InjectMock
    CandidateService candidateService;

    @Test
    void create() {
        CreateCandidate dto = Instancio.create(CreateCandidate.class);
        ArgumentCaptor<Candidate> captor = ArgumentCaptor.forClass(Candidate.class);

        candidateApi.create(dto);

        verify(candidateService).save(captor.capture());
        verifyNoMoreInteractions(candidateService);

        Candidate candidate = captor.getValue();

        assertEquals(candidate.photo(), dto.photo());
        assertEquals(candidate.givenName(), dto.givenName());
        assertEquals(candidate.familyName(), dto.familyName());
        assertEquals(candidate.email(), dto.email());
        assertEquals(candidate.phone(), dto.phone());
        assertEquals(candidate.jobTitle(), dto.jobTitle());
    }

    @Test
    void list() {
        var candidates = Instancio.stream(Candidate.class).limit(10).toList();

        when(candidateService.findAll()).thenReturn(candidates);

        var response = candidateApi.list();

        verify(candidateService).findAll();
        verifyNoMoreInteractions(candidateService);

        assertEquals(candidates.stream().map(api.dto.out.Candidate::fromDomain).toList(), response);
    }
}