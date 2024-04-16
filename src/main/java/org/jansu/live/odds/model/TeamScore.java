package org.jansu.live.odds.model;

/**
 * TeamScore member, extends Team member with information about score
 */
public class TeamScore extends Team {

    private int teamScore;

    /**
     * Constructor
     * @param teamName
     */
    public TeamScore(String teamName) {
        super();
        this.setTeam(teamName);
        this.teamScore = 0;
    }

    /**
     * Constructor
     * @param teamName
     * @param teamScore
     */
    public TeamScore(String teamName, int teamScore) {
        super();
        this.setTeam(teamName);
        this.teamScore = teamScore;
    }

    /**
     * TeamScore setter
     * @param teamScore
     */
    public void setTeamScore(Integer teamScore) {
        this.teamScore = teamScore;
    }

    /**
     * TeamScore getter
     * @return int TeamScore member info regarding score
     */
    public int getTeamScore() {
        return  this.teamScore;
    }
}
