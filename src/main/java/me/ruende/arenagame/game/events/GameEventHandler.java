package me.ruende.arenagame.game.events;

import me.ruende.arenagame.game.controller.GameController;
import me.ruende.arenagame.game.controller.GameControllerImpl;
import me.ruende.arenagame.game.controller.GameCoordinator;
import me.ruende.arenagame.game.round.RoundController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameEventHandler implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (GameCoordinator.hasGameController(player)) {
            GameController controller = GameCoordinator.getOrCreateGameController(player);
            controller.stopGame();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (GameCoordinator.hasGameController(player)) {
            GameController controller = GameCoordinator.getOrCreateGameController(player);
            controller.stopGame();
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            Player player = event.getEntity().getKiller();
            if (GameCoordinator.hasGameController(player)) {
                GameController controller = GameCoordinator.getOrCreateGameController(player);
                if (controller instanceof GameControllerImpl gameControllerImpl) {
                    RoundController roundController = gameControllerImpl.getRoundManager();
                    if (roundController != null) {
                        roundController.checkRoundClear();
                    }
                }
            }
        }
    }
}
