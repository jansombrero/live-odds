package org.jansu.live.odds.service.integration.test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@Tag("integration")
public class LiveOddsServiceTestIT {

    @Test
    public void testLiveOddsService() {

        // Test POST start match
        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .body("{\"homeTeam\": \"Germany\", \"awayTeam\": \"France\"}")
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
        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .body("{\"homeTeam\": \"Germany\", \"awayTeam\": \"France\", " +
                        "\"homeTeamScore\": 2, \"awayTeamScore\": 1}")
                .when()
                .put("/live-odds")
                .then()
                .statusCode(204);

        // Test DELETE finish match
        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                .when()
                .delete("/live-odds?homeTeam=Germany&awayTeam=France")
                .then()
                .statusCode(204);
    }
}
