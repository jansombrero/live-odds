package org.jansu.live.odds.service;

import org.jansu.live.odds.model.Game;
import org.jansu.live.odds.model.TeamScore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiveOddsServiceTest {

    @Test
    public void testGetLiveMatches() {
        LiveOddsService liveOddsService = new LiveOddsService();
        List<Game> liveGames = liveOddsService.getLiveMatches();
        Assertions.assertEquals(0, liveGames.size());
    }

    @Test
    public void testStartMatch() throws Exception {
        LiveOddsService liveOddsService = new LiveOddsService();
        int ret = liveOddsService.startMatch("Mexico", "Australia");
        Assertions.assertEquals(0, ret);
    }

    @Test
    public void testUpdateScore() throws Exception {
        LiveOddsService liveOddsService = new LiveOddsService();
        TeamScore homeTeamScore = new TeamScore("Spain", 2);
        TeamScore awayTeamScore = new TeamScore("Argentina", 3);
        int ret = liveOddsService.updateScore(homeTeamScore, awayTeamScore);
        Assertions.assertEquals(0, ret);
    }

    @Test
    public void testFinishMatch() throws Exception {
        LiveOddsService liveOddsService = new LiveOddsService();
        Game game = new Game();
        TeamScore homeTeamScore = new TeamScore("Germany", 5);
        TeamScore awayTeamScore = new TeamScore("Uruguay", 5);
        Map<TeamScore, TeamScore> map = new HashMap<>();
        map.put(homeTeamScore, awayTeamScore);
        game.setGame(map);
        int ret = liveOddsService.finishMatch(game);
        Assertions.assertEquals(0, ret);
    }
}
