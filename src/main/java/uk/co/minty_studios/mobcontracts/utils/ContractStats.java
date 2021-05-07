package uk.co.minty_studios.mobcontracts.utils;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public class ContractStats {

    private String contractTier;
    private String effectType;
    private EntityType entityType;
    private UUID uuid;

    public ContractStats(UUID u, String tier, String type, EntityType mobType) {
        contractTier = tier;
        effectType = type;
        entityType = mobType;
        uuid = u;
    }

    public UUID getUuid(){
        return uuid;
    }

    public String getTier(){
        return contractTier;
    }

    public String getType(){
        return effectType;
    }

    public EntityType getMobType(){
        return entityType;
    }
}
