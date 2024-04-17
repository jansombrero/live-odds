package org.jansu.live.odds.service.integration.test;

public class Match {

    private String homeTeam;
    private String awayTeam;

    public Match() {}

    public Match(String home, String away) {
        this.homeTeam = home;
        this.awayTeam = away;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }
}
