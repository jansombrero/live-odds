package org.jansu.live.odds.service;

import org.jansu.live.odds.exception.LiveOddsException;
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
    public void testStartMatch() throws LiveOddsException {
        LiveOddsService liveOddsService = new LiveOddsService();

        int ret = liveOddsService.startMatch("Mexico", "Australia");
        Assertions.assertEquals(0, ret);

        List<Game> liveGames = liveOddsService.getLiveMatches();
        Assertions.assertEquals(1, liveGames.size());
    }

    @Test
    public void testStartMatch_MatchAlreadyExists() throws LiveOddsException {
        LiveOddsService liveOddsService = new LiveOddsService();

        int ret = liveOddsService.startMatch("Mexico", "Italy");
        Assertions.assertEquals(0, ret);

        LiveOddsException liveOddsException = Assertions.assertThrows(LiveOddsException.class, () ->
                liveOddsService.startMatch("Mexico", "Italy"));
        Assertions.assertEquals("Match is already started.", liveOddsException.getMessage());
    }

    @Test
    public void testStartMatch_HomeOrAwayTeamIsAlreadyPlayingMatch() throws LiveOddsException {
        LiveOddsService liveOddsService = new LiveOddsService();

        int ret = liveOddsService.startMatch("Spain", "Canada");
        Assertions.assertEquals(0, ret);

        LiveOddsException liveOddsException = Assertions.assertThrows(LiveOddsException.class, () ->
                liveOddsService.startMatch("Spain", "Italy"));
        Assertions.assertEquals("Home or away team is already playing a match.",
                liveOddsException.getMessage());
    }

    @Test
    public void testStartMatch_HomeOrAwayTeamIsEmptyString() throws LiveOddsException {
        LiveOddsService liveOddsService = new LiveOddsService();

        LiveOddsException liveOddsException = Assertions.assertThrows(LiveOddsException.class, () ->
                liveOddsService.startMatch("", "Italy"));
        Assertions.assertEquals("Home or away team contains empty string.", liveOddsException.getMessage());
    }

    @Test
    public void testUpdateScore() throws LiveOddsException {
        LiveOddsService liveOddsService = new LiveOddsService();

        int ret = liveOddsService.startMatch("Spain", "Argentina");
        Assertions.assertEquals(0, ret);
        TeamScore homeTeamScore = new TeamScore("Spain", 2);
        TeamScore awayTeamScore = new TeamScore("Argentina", 3);
        ret = liveOddsService.updateScore(homeTeamScore, awayTeamScore);
        Assertions.assertEquals(0, ret);
    }

    @Test
    public void testUpdateScore_MatchDoesNotExist() throws LiveOddsException {
        LiveOddsService liveOddsService = new LiveOddsService();

        LiveOddsException liveOddsException = Assertions.assertThrows(LiveOddsException.class, () -> {
            TeamScore homeTeamScore = new TeamScore("Spain", 2);
            TeamScore awayTeamScore = new TeamScore("Argentina", 3);
            liveOddsService.updateScore(homeTeamScore, awayTeamScore);
        });
        Assertions.assertEquals("Match does not exist.", liveOddsException.getMessage());
    }

    @Test
    public void testFinishMatch() throws LiveOddsException {
        LiveOddsService liveOddsService = new LiveOddsService();

        int ret = liveOddsService.startMatch("Germany", "Uruguay");
        Assertions.assertEquals(0, ret);

        List<Game> liveGames = liveOddsService.getLiveMatches();
        Assertions.assertEquals(1, liveGames.size());

        Game game = liveGames.get(0);
        ret = liveOddsService.finishMatch(game);
        Assertions.assertEquals(0, ret);
    }

    @Test
    public void testFinishMatch_MatchDoesNotExists() throws LiveOddsException {
        LiveOddsService liveOddsService = new LiveOddsService();

        LiveOddsException liveOddsException = Assertions.assertThrows(LiveOddsException.class, () -> {
            TeamScore homeTeamScore = new TeamScore("Brazil", 5);
            TeamScore awayTeamScore = new TeamScore("France", 5);
            Game game = new Game(homeTeamScore, awayTeamScore);
            liveOddsService.finishMatch(game);
        });

        Assertions.assertEquals("Match does not exist.", liveOddsException.getMessage());
    }
}
