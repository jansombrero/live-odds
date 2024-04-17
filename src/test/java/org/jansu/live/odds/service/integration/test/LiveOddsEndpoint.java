package org.jansu.live.odds.service.integration.test;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jansu.live.odds.exception.LiveOddsException;
import org.jansu.live.odds.model.Game;
import org.jansu.live.odds.model.TeamScore;
import org.jansu.live.odds.service.LiveOddsService;

import java.util.List;

@Path("/live-odds")
public class LiveOddsEndpoint {

    @Inject
    LiveOddsService liveOddsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Game> getLiveMatches() {
        return liveOddsService.getLiveMatches();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response startMatch(Match match) throws LiveOddsException {
        liveOddsService.startMatch(match.getHomeTeam(), match.getAwayTeam());
        return Response.status(201).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateScore(MatchScore matchScore) throws LiveOddsException {
        TeamScore homeTeamScore = new TeamScore(matchScore.getHomeTeam(), matchScore.getHomeTeamScore());
        TeamScore awayTeamScore = new TeamScore(matchScore.getAwayTeam(), matchScore.getAwayTeamScore());

        liveOddsService.updateScore(homeTeamScore, awayTeamScore);
        return Response.status(204).build();
    }

    @DELETE
    public Response finishMatch(@QueryParam("homeTeam") String home,
                                @QueryParam("awayTeam") String away) throws LiveOddsException {
        Game game = liveOddsService.getLiveMatches().stream()
                .filter(x -> x.getGame().entrySet().stream().allMatch(entry ->
                        entry.getKey().getTeam().equals(home) &&
                                entry.getValue().getTeam().equals(away))).toList().stream()
                .findFirst().orElse(null);

        if (game == null)
            return Response.status(500).build();

        liveOddsService.finishMatch(game);
        return Response.status(204).build();
    }
}
