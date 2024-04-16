package org.jansu.live.odds.model;

import java.util.Collections;
import java.util.Map;

/**
 * Game class member
 */
public class Game {

    private Map<TeamScore, TeamScore> game;
    /**
     * Index of entry
     */
    private int index;

    /**
     * Constructor
     * @param homeTeamScore
     * @param awayTeamScore
     * @param index
     */
    public Game(TeamScore homeTeamScore, TeamScore awayTeamScore, int index) {
        Map<TeamScore, TeamScore> mapGame = Collections.singletonMap(homeTeamScore, awayTeamScore);
        this.setGame(mapGame);
        this.index = index;
    }

    /**
     * Constructor
     * @param mapGame
     * @param index
     */
    public Game(Map<TeamScore, TeamScore> mapGame, int index) {
        this.setGame(mapGame);
        this.index = index;
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
        return this.game;
    }

    /**
     * Index setter
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Index getter
     * @return int Index of match when added to games in progress
     */
    public int getIndex() {
        return this.index;
    }
}
