package org.jansu.live.odds.service;

import org.jansu.live.odds.exception.LiveOddsException;
import org.jansu.live.odds.model.Game;
import org.jansu.live.odds.model.TeamScore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

        List<Game> liveGames = liveOddsService.getLiveMatches();
        Assertions.assertEquals(1, liveGames.size());

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
        liveOddsException = Assertions.assertThrows(LiveOddsException.class, () ->
                liveOddsService.startMatch("Argentina", "Canada"));
        Assertions.assertEquals("Home or away team is already playing a match.",
                liveOddsException.getMessage());
    }

    @Test
    public void testStartMatch_HomeOrAwayTeamIsEmptyString() throws LiveOddsException {
        LiveOddsService liveOddsService = new LiveOddsService();

        LiveOddsException liveOddsException = Assertions.assertThrows(LiveOddsException.class, () ->
                liveOddsService.startMatch("", "Italy"));
        Assertions.assertEquals("Home or away team contains empty string.", liveOddsException.getMessage());
        liveOddsException = Assertions.assertThrows(LiveOddsException.class, () ->
                liveOddsService.startMatch("Spain", ""));
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

        List<Game> liveGames = liveOddsService.getLiveMatches();
        Game updatedGame = liveGames.stream().filter(x -> x.getGame().entrySet().stream().allMatch(entry ->
                        entry.getKey().getTeam().equals("Spain") &&
                                entry.getValue().getTeam().equals("Argentina"))).toList().stream()
                .findFirst().orElse(null);
        Assertions.assertNotNull(updatedGame);
        Map<TeamScore, TeamScore> gameMap = updatedGame.getGame();
        Map.Entry<TeamScore, TeamScore> teamScoreMap = gameMap.entrySet().stream().findFirst().orElse(null);
        Assertions.assertNotNull(teamScoreMap);
        Assertions.assertEquals("Spain", teamScoreMap.getKey().getTeam());
        Assertions.assertEquals("Argentina", teamScoreMap.getValue().getTeam());
        Assertions.assertEquals(2, teamScoreMap.getKey().getTeamScore());
        Assertions.assertEquals(3, teamScoreMap.getValue().getTeamScore());
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

        int ret = liveOddsService.startMatch("Mexico", "Italy");
        Assertions.assertEquals(0, ret);

        List<Game> liveGames = liveOddsService.getLiveMatches();
        Assertions.assertEquals(1, liveGames.size());

        liveOddsException = Assertions.assertThrows(LiveOddsException.class, () -> {
            TeamScore homeTeamScore = new TeamScore("Mexico", 2);
            TeamScore awayTeamScore = new TeamScore("Argentina", 3);
            liveOddsService.updateScore(homeTeamScore, awayTeamScore);
        });
        Assertions.assertEquals("Match does not exist.", liveOddsException.getMessage());
    }

    @Test
    public void testStartUpdateAndGetLiveMatches() throws LiveOddsException {
        LiveOddsService liveOddsService = new LiveOddsService();

        // Start matches
        int ret = liveOddsService.startMatch("Mexico", "Canada");
        Assertions.assertEquals(0, ret);
        ret = liveOddsService.startMatch("Spain", "Brazil");
        Assertions.assertEquals(0, ret);
        ret = liveOddsService.startMatch("Germany", "France");
        Assertions.assertEquals(0, ret);
        ret = liveOddsService.startMatch("Uruguay", "Italy");
        Assertions.assertEquals(0, ret);
        ret = liveOddsService.startMatch("Argentina", "Australia");
        Assertions.assertEquals(0, ret);

        // Update scores
        TeamScore homeTeamScore = new TeamScore("Mexico", 0);
        TeamScore awayTeamScore = new TeamScore("Canada", 5);
        ret = liveOddsService.updateScore(homeTeamScore, awayTeamScore);
        Assertions.assertEquals(0, ret);
        homeTeamScore = new TeamScore("Spain", 10);
        awayTeamScore = new TeamScore("Brazil", 2);
        ret = liveOddsService.updateScore(homeTeamScore, awayTeamScore);
        Assertions.assertEquals(0, ret);
        homeTeamScore = new TeamScore("Germany", 2);
        awayTeamScore = new TeamScore("France", 2);
        ret = liveOddsService.updateScore(homeTeamScore, awayTeamScore);
        Assertions.assertEquals(0, ret);
        homeTeamScore = new TeamScore("Uruguay", 6);
        awayTeamScore = new TeamScore("Italy", 6);
        ret = liveOddsService.updateScore(homeTeamScore, awayTeamScore);
        Assertions.assertEquals(0, ret);
        homeTeamScore = new TeamScore("Argentina", 3);
        awayTeamScore = new TeamScore("Australia", 1);
        ret = liveOddsService.updateScore(homeTeamScore, awayTeamScore);
        Assertions.assertEquals(0, ret);

        // Get live matches
        List<Game> liveGames = liveOddsService.getLiveMatches();
        Assertions.assertEquals(5, liveGames.size());
        Game updatedGame = liveGames.get(0);
        Assertions.assertNotNull(updatedGame);
        Map<TeamScore, TeamScore> gameMap = updatedGame.getGame();
        Map.Entry<TeamScore, TeamScore> teamScoreMap = gameMap.entrySet().stream().findFirst().orElse(null);
        Assertions.assertNotNull(teamScoreMap);
        Assertions.assertEquals("Uruguay", teamScoreMap.getKey().getTeam());
        Assertions.assertEquals("Italy", teamScoreMap.getValue().getTeam());
        Assertions.assertEquals(6, teamScoreMap.getKey().getTeamScore());
        Assertions.assertEquals(6, teamScoreMap.getValue().getTeamScore());
        updatedGame = liveGames.get(1);
        Assertions.assertNotNull(updatedGame);
        gameMap = updatedGame.getGame();
        teamScoreMap = gameMap.entrySet().stream().findFirst().orElse(null);
        Assertions.assertNotNull(teamScoreMap);
        Assertions.assertEquals("Spain", teamScoreMap.getKey().getTeam());
        Assertions.assertEquals("Brazil", teamScoreMap.getValue().getTeam());
        Assertions.assertEquals(10, teamScoreMap.getKey().getTeamScore());
        Assertions.assertEquals(2, teamScoreMap.getValue().getTeamScore());
        updatedGame = liveGames.get(2);
        Assertions.assertNotNull(updatedGame);
        gameMap = updatedGame.getGame();
        teamScoreMap = gameMap.entrySet().stream().findFirst().orElse(null);
        Assertions.assertNotNull(teamScoreMap);
        Assertions.assertEquals("Mexico", teamScoreMap.getKey().getTeam());
        Assertions.assertEquals("Canada", teamScoreMap.getValue().getTeam());
        Assertions.assertEquals(0, teamScoreMap.getKey().getTeamScore());
        Assertions.assertEquals(5, teamScoreMap.getValue().getTeamScore());
        updatedGame = liveGames.get(3);
        Assertions.assertNotNull(updatedGame);
        gameMap = updatedGame.getGame();
        teamScoreMap = gameMap.entrySet().stream().findFirst().orElse(null);
        Assertions.assertNotNull(teamScoreMap);
        Assertions.assertEquals("Argentina", teamScoreMap.getKey().getTeam());
        Assertions.assertEquals("Australia", teamScoreMap.getValue().getTeam());
        Assertions.assertEquals(3, teamScoreMap.getKey().getTeamScore());
        Assertions.assertEquals(1, teamScoreMap.getValue().getTeamScore());
        updatedGame = liveGames.get(4);
        Assertions.assertNotNull(updatedGame);
        gameMap = updatedGame.getGame();
        teamScoreMap = gameMap.entrySet().stream().findFirst().orElse(null);
        Assertions.assertNotNull(teamScoreMap);
        Assertions.assertEquals("Germany", teamScoreMap.getKey().getTeam());
        Assertions.assertEquals("France", teamScoreMap.getValue().getTeam());
        Assertions.assertEquals(2, teamScoreMap.getKey().getTeamScore());
        Assertions.assertEquals(2, teamScoreMap.getValue().getTeamScore());
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
            Game game = new Game(homeTeamScore, awayTeamScore, 0);
            liveOddsService.finishMatch(game);
        });

        Assertions.assertEquals("Match does not exist.", liveOddsException.getMessage());
    }
}
