package org.jansu.live.odds.service.integration.test;

public class MatchScore extends Match {

    private int homeTeamScore;
    private int awayTeamScore;

    public MatchScore(String home, String away, int homeScore, int awayScore) {
        this.setHomeTeam(home);
        this.setHomeTeamScore(homeScore);
        this.setAwayTeam(away);
        this.setAwayTeamScore(awayScore);
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
