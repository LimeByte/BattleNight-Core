package me.limebyte.battlenight.api.tosort;

import me.limebyte.battlenight.core.Battle;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class BattleDeathEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private Battle battle;
    private Location respawnLocation;
    private boolean cancel = false;

    public BattleDeathEvent(Battle battle, Player player) {
        super(player);
        this.battle = battle;
        this.respawnLocation = battle.getArena().getRandomSpawnPoint().getLocation();
    }

    public Battle getBattle() {
        return battle;
    }

    public Location getRespawnLocation() {
        return respawnLocation;
    }

    public void setRespawnLocation(Location respawnLocation) {
        this.respawnLocation = respawnLocation;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}