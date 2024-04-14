package org.jansu.live.odds.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.jansu.live.odds.model.Game;
import org.jansu.live.odds.model.TeamScore;

import java.util.ArrayList;
import java.util.List;

/**
 * Live Football World Cup Scoreboard service
 */
@ApplicationScoped
public class LiveOddsService {

    List<Game> gameList = new ArrayList<>();

    /**
     * Start a new match. Initial score is 0 - 0 and match is added to the scoreboard
     * @param homeTeam
     * @param awayTeam
     * @return int 0 if successful
     * @throws Exception Exception is thrown if match couldn't be started.
     */
    public int startMatch(String homeTeam, String awayTeam) throws Exception {
        return 0;
    }

    /**
     * Update score
     * @param homeTeamScore
     * @param awayTeamScore
     * @return int 0 if successful
     * @throws Exception Exception is thrown if the score cannot be updated
     */
    public int updateScore(TeamScore homeTeamScore, TeamScore awayTeamScore) throws Exception {
        return 0;
    }

    /**
     * Finish selected match in progress.
     * @param game
     * @return int 0 if successful
     * @throws Exception Exception is thrown if match cannot be finished
     */
    public int finishMatch(Game game) throws Exception {
        return 0;
    }

    /**
     * Get a summary of matches in progress ordered by their total score.
     * The matches with the same total score will be returned ordered by the most recently started match
     * in the scoreboard.
     * @return List<Game> Return list of matches in progress
     */
    public List<Game> getLiveMatches() {
        return gameList;
    }
}
