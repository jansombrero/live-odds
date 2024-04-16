package org.jansu.live.odds.service.integration.test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.MediaType;
import org.hamcrest.CoreMatchers;
import org.jansu.live.odds.service.LiveOddsService;
import org.jansu.live.odds.service.LiveOddsServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@Tag("integration")
public class LiveOddsServiceTestIT {

    @Inject
    LiveOddsService liveOddsService;

    @Test
    public void testLiveOddsService() {

        // Test POST start match
        Match match = new Match("Germany", "France");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(match)
                .when()
                .post("/live-odds")
                .then()
                .statusCode(201);

        // Test GET live matches
        given()
                .when()
                .get("/live-odds")
                .then()
                .statusCode(200)
                .body("$.size()", is(1));

        // Test PUT update score
        MatchScore matchScore = new MatchScore("Germany", "France", 2, 1);
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(matchScore)
                .when()
                .put("/live-odds")
                .then()
                .statusCode(204);

        // Test DELETE finish match
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .delete("/live-odds?homeTeam=Germany&?awayTeam=France&?index=0")
                .then()
                .statusCode(204);
    }
}
