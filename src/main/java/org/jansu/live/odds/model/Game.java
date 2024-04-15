package org.jansu.live.odds.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Game class member
 */
public class Game {

    private Map<TeamScore, TeamScore> game;

    public Game(TeamScore homeTeamScore, TeamScore awayTeamScore) {
        Map<TeamScore, TeamScore> mapGame = new HashMap<>();
        mapGame.put(homeTeamScore, awayTeamScore);
        this.setGame(mapGame);
    }

    /**
     * Game setter
     * @param game
     */
    public void setGame(Map<TeamScore, TeamScore> game) {
        this.game = game;
    }

    /**
     * Game getter
     * @return Map Game with home and away team along with the score
     */
    public Map<TeamScore, TeamScore> getGame() {
        return game;
    }
}
