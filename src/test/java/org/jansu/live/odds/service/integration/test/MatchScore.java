package org.jansu.live.odds.service.integration.test;

public class MatchScore extends Match {

    private int homeTeamScore;
    private int awayTeamScore;

    public MatchScore() {}

    public MatchScore(String home, String away, int homeScore, int awayScore) {
        this.setHomeTeam(home);
        this.homeTeamScore = homeScore;
        this.setAwayTeam(away);
        this.awayTeamScore = awayScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setAwayTeamScore(int awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }
}
