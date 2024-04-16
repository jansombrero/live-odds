package org.jansu.live.odds.service;

import io.quarkus.runtime.util.StringUtil;
import jakarta.enterprise.context.ApplicationScoped;
import org.jansu.live.odds.exception.LiveOddsException;
import org.jansu.live.odds.model.Game;
import org.jansu.live.odds.model.TeamScore;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
import java.util.Comparator;

/**
 * Live Football World Cup Scoreboard service
 */
@ApplicationScoped
public class LiveOddsService {

    private final static int SUCCESS = 0;
    /**
     * Index number for entry in list of games in progress
     */
    private int index = 0;
    /**
     * List for games in progress
     */
    private final List<Game> gameList = new ArrayList<>();

    /**
     * Start a new match. Initial score is 0 - 0 and match is added to the scoreboard
     * @param homeTeam
     * @param awayTeam
     * @return int 0 if successful
     * @throws LiveOddsException LiveOddsException is thrown if match couldn't be started.
     */
    public int startMatch(String homeTeam, String awayTeam) throws LiveOddsException {

        // Verify if homeTeam and awayTeam is not empty
        if (StringUtil.isNullOrEmpty(homeTeam) || StringUtil.isNullOrEmpty(awayTeam))
            throw new LiveOddsException("Home or away team contains empty string.");

        TeamScore homeTeamScore = new TeamScore(homeTeam);
        TeamScore awayTeamScore = new TeamScore(awayTeam);
        Map<TeamScore, TeamScore> gameMap = Collections.singletonMap(homeTeamScore, awayTeamScore);

        // Check if match already exists
        if (!gameList.stream().filter(x -> x.getGame().entrySet().stream().allMatch(entry ->
                entry.getKey().getTeam().equals(homeTeam) &&
                entry.getValue().getTeam().equals(awayTeam)))
                .toList().isEmpty())
            throw new LiveOddsException("Match is already started.");

        // Check if home or away team already plays a match
        if (!gameList.stream().filter(x -> x.getGame().entrySet().stream().anyMatch(entry ->
                    entry.getKey().getTeam().equals(homeTeam) || entry.getKey().getTeam().equals(awayTeam) ||
                    entry.getValue().getTeam().equals(homeTeam) || entry.getValue().getTeam().equals(awayTeam)))
                .toList().isEmpty())
            throw new LiveOddsException("Home or away team is already playing a match.");

        gameList.add(new Game(gameMap, index));
        index++;

        return SUCCESS;
    }

    /**
     * Update score
     * @param homeTeamScore
     * @param awayTeamScore
     * @return int 0 if successful
     * @throws LiveOddsException LiveOddsException is thrown if the score cannot be updated
     */
    public int updateScore(TeamScore homeTeamScore, TeamScore awayTeamScore) throws LiveOddsException {

        // Check if match exists
        Game updateGame = gameList.stream().filter(x -> x.getGame().entrySet().stream().allMatch(entry ->
                entry.getKey().getTeam().equals(homeTeamScore.getTeam()) &&
                        entry.getValue().getTeam().equals(awayTeamScore.getTeam()))).findFirst().orElse(null);
        if (updateGame == null)
            throw new LiveOddsException("Match does not exist.");

        gameList.set(updateGame.getIndex(), new Game(homeTeamScore, awayTeamScore, updateGame.getIndex()));

        return SUCCESS;
    }

    /**
     * Finish selected match in progress.
     * @param game
     * @return int 0 if successful
     * @throws LiveOddsException LiveOddsException is thrown if match cannot be finished
     */
    public int finishMatch(Game game) throws LiveOddsException {

        if (!gameList.remove(game))
            throw new LiveOddsException("Match does not exist.");

        return SUCCESS;
    }

    /**
     * Get a summary of matches in progress ordered by their total score.
     * The matches with the same total score will be returned ordered by the most recently started match
     * in the scoreboard.
     * @return List<Game> Return list of matches in progress
     */
    public List<Game> getLiveMatches() {

        gameList.sort(new Comparator<Game>() {
            @Override
            public int compare(Game game1, Game game2) {
                Map.Entry<TeamScore, TeamScore> teamScoreMap1 =
                        game1.getGame().entrySet().stream().findFirst().orElse(null);
                Map.Entry<TeamScore, TeamScore> teamScoreMap2 =
                        game2.getGame().entrySet().stream().findFirst().orElse(null);
                if ((teamScoreMap1.getKey().getTeamScore() + teamScoreMap1.getValue().getTeamScore()) ==
                        (teamScoreMap2.getKey().getTeamScore() + teamScoreMap2.getValue().getTeamScore()))
                    return Integer.compare(game2.getIndex(), game1.getIndex());

                return Integer.compare((teamScoreMap2.getKey().getTeamScore() + teamScoreMap2.getValue().getTeamScore()),
                        (teamScoreMap1.getKey().getTeamScore() + teamScoreMap1.getValue().getTeamScore()));
            }
        });

        return gameList;
    }
}
