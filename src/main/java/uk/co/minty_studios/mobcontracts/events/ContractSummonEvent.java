package uk.co.minty_studios.mobcontracts.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nullable;

public class ContractSummonEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String name;
    private double damage;
    private double speed;
    private double health;
    private double armor;
    private String tier;
    private String mobType;
    private Player player;
    private Entity entity;
    private String effect;

    public ContractSummonEvent(Entity entity, String name, double damage, double speed, double health, double armor, String tier, String effect, String mobType, Player player){
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
    }

    public String getEffect(){
        return effect;
    }

    public Entity getEntity(){
        return entity;
    }

    public String getName(){
        return name;
    }

    public double getDamage(){
        return damage;
    }

    public double getSpeed(){
        return speed;
    }

    public double getHealth(){
        return health;
    }

    public double getArmor(){
        return armor;
    }

    public String getTier(){
        return tier;
    }

    public String getMobType(){
        return mobType;
    }

    public Player getPlayer(){
        return player;
    }


    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
