package me.ruende.arenagame.game.controller;

public interface GameController {

    boolean isStarted();

    void startGame();

    void stopGame();

    boolean isGameActive();
}
