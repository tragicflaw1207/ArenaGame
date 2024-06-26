package me.ruende.arenagame.game.round;

public class Round {
    private int currentRound;

    public Round() {
        this.currentRound = 1;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void nextRound() {
        currentRound++;
    }

    public void reset() {
        currentRound = 1;
    }
}
