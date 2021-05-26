package uk.co.minty_studios.mobcontracts.utils;

import org.bukkit.entity.EntityType;

import java.util.Date;
import java.util.UUID;

public class ContractObject {

    private final UUID playerUuid;
    private final UUID contractUuid;
    private final String displayName;
    private final String tier;
    private final EntityType mobType;
    private final int health;
    private final int damage;
    private final long date;
    private final String summonerName;

    public ContractObject(UUID playerUuid, String summonerName, UUID contractUuid, String displayName, String tier, EntityType mobType, int health, int damage, long date) {
        this.playerUuid = playerUuid;
        this.contractUuid = contractUuid;
        this.displayName = displayName;
        this.tier = tier;
        this.mobType = mobType;
        this.health = health;
        this.damage = damage;
        this.date = date;
        this.summonerName = summonerName;
    }

    public UUID getSummonerUuid() {
        return playerUuid;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public UUID getContractUuid() {
        return contractUuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getTier() {
        return tier;
    }

    public EntityType getMobType() {
        return mobType;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public long getDate() {
        return date;
    }
}
