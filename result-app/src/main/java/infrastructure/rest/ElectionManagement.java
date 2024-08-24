package infrastructure.rest;

import api.dto.Election;

import java.util.List;

@RegisterRestClient
public interface ElectionManagement {

    @GET
    @Path("/api/elections")
    Uni<List<Election>> getElections();
}
