package fr.tick.scoreboard.manager;


import fr.tick.scoreboard.ScoreboardUtils;

public class ScoreboardManager {

    private ScoreboardUtils scoreboardUtils;

    public ScoreboardManager() {
        scoreboardUtils = new ScoreboardUtils();
    }

    public ScoreboardUtils getScoreboardUtils() {
        return scoreboardUtils;
    }
}
