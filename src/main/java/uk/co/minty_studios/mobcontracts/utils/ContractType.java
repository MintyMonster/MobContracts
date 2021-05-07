package uk.co.minty_studios.mobcontracts.utils;

import org.bukkit.entity.EntityType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ContractType {
    private static final Map<UUID, ContractStats> stats = new HashMap<>();

    public void addContract(UUID uuid, String tier, String type, EntityType mobType) {
        if (!contractExists(uuid)) {
            ContractStats contractStats = new ContractStats(uuid, tier, type, mobType);
            stats.put(uuid, contractStats);
        }
    }

    public void removeContract(UUID uuid){
        if(contractExists(uuid))
            stats.remove(uuid);
    }

    public void removeAllContracts(){
        for(Map.Entry<UUID, ContractStats> entry : stats.entrySet())
            stats.remove(entry.getKey());
    }

    public Boolean contractExists(UUID uuid){
        for(Map.Entry<UUID, ContractStats> entry : stats.entrySet())
            if(entry.getKey().equals(uuid))
                return true;
        return false;
    }

    public String getContractTier(UUID uuid){
        for(Map.Entry<UUID, ContractStats> entry : stats.entrySet())
            if(entry.getKey().equals(uuid))
                return entry.getValue().getTier();
        return "Empty";
    }

    public String getEffectType(UUID uuid){
        for(Map.Entry<UUID, ContractStats> entry : stats.entrySet())
            if(entry.getKey().equals(uuid))
                return entry.getValue().getType();
        return "No effect";
    }

    public EntityType getEntityType(UUID uuid){
        for(Map.Entry<UUID, ContractStats> entry : stats.entrySet())
            if(entry.getKey().equals(uuid))
                return entry.getValue().getMobType();
        return null;
    }
}
