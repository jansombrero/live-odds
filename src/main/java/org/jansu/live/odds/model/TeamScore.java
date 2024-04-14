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
        this.setTeam(teamName);
        this.setTeamScore(0);
    }

    /**
     * Constructor
     * @param teamName
     * @param teamScore
     */
    public TeamScore(String teamName, int teamScore) {
        this.setTeam(teamName);
        this.setTeamScore(teamScore);
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
        return  teamScore;
    }
}
