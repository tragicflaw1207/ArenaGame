package me.ruende.arenagame;

import me.ruende.arenagame.commands.GameCommand;
import me.ruende.arenagame.game.controller.GameController;
import me.ruende.arenagame.game.controller.GameCoordinator;
import me.ruende.arenagame.game.events.GameEventHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArenaGame extends JavaPlugin {
    private static ArenaGame instance;

    public static ArenaGame getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getCommandMap().register("game", new GameCommand("game"));

        getServer().getPluginManager().registerEvents(new GameEventHandler(), this);
    }

    @Override
    public void onDisable() {
        GameCoordinator.getGameControllers().forEach(GameController::stopGame);
    }
}
