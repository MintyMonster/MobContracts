package uk.co.minty_studios.mobcontracts.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class ContractSummonEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final String name;
    private final double damage;
    private final double speed;
    private final double health;
    private final double armor;
    private final String tier;
    private final String mobType;
    private final Player player;
    private final Entity entity;
    private final String effect;
    private final UUID uuid;

    public ContractSummonEvent(Entity entity, UUID uuid, String name, double damage, double speed, double health, double armor, String tier, String effect, String mobType, Player player) {
        this.name = name;
        this.damage = damage;
        this.speed = speed;
        this.health = health;
        this.armor = armor;
        this.tier = tier;
        this.mobType = mobType;
        this.player = player;
        this.entity = entity;
        this.effect = effect;
        this.uuid = uuid;
    }

    public String getEffect() {
        return effect;
    }

    public Entity getEntity() {
        return entity;
    }

    public String getName() {
        return name;
    }

    public double getDamage() {
        return damage;
    }

    public double getSpeed() {
        return speed;
    }

    public double getHealth() {
        return health;
    }

    public double getArmor() {
        return armor;
    }

    public String getTier() {
        return tier;
    }

    public String getMobType() {
        return mobType;
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUuid() { return uuid; }


    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
