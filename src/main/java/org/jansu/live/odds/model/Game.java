package org.jansu.live.odds.model;

import java.util.Map;

/**
 * Game class member
 */
public class Game {

    private Map<TeamScore, TeamScore> game;

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
