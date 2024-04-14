package org.jansu.live.odds.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Tag;

@QuarkusTest
@Tag("integration")
public class LiveOddsServiceTestIT extends LiveOddsServiceTest {

    @Inject
    LiveOddsService liveOddsService;
}
