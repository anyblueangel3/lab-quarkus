package infrastructure.resources;

import api.dto.Election;
import infrastructure.rest.ElectionManagement;

import java.awt.PageAttributes.MediaType;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Path("/")
public class ResultResource {

    private final ElectionManagement electionManagement;

    public ResultResource(@RestClient ElectionManagement electionManagement) {
        this.electionManagement = electionManagement;
    }

    @GET
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public Multi<List<Election>> results() {
        return Multi.createFrom()
                    .ticks()
                    .every(Duration.of(5, ChronoUnit.SECONDS))
                    .onItem()
                    .transformToMultiAndMerge(n -> electionManagement.getElections().toMulti());

    }
}
