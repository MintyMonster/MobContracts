package uk.co.minty_studios.mobcontracts.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CurrentContracts {

    private static final Map<UUID, Entity> contracts = new HashMap<>();

    public void addEntity(Player player, Entity entity){
        contracts.put(player.getUniqueId(), entity);
    }

    public Entity getEntity(Player player){
        Entity entity = null;
        for(Map.Entry<UUID, Entity> entry : contracts.entrySet())
            if(entry.getKey().equals(player.getUniqueId()))
                entity = entry.getValue();

        return entity;
    }

    public boolean inContract(Player player){
        for(Map.Entry<UUID, Entity> entry : contracts.entrySet())
            if(entry.getKey().equals(player.getUniqueId()))
                return true;
        return false;
    }

    public Boolean isContract(Entity entity){
        for(Map.Entry<UUID, Entity> entry : contracts.entrySet())
            if(entry.getValue().equals(entity))
                return true;
        return false;
    }

    public void removeAllContracts(){ // For removing all contracts command
        for(Map.Entry<UUID, Entity> entry : contracts.entrySet()){
            entry.getValue().remove();
            contracts.remove(entry.getKey());
        }

    }

    public void removePlayerContract(Player player){ // Remove single contract based on player
        for(Map.Entry<UUID, Entity> entry : contracts.entrySet())
            if(entry.getKey().equals(player.getUniqueId())){
                entry.getValue().remove();

            }
        contracts.remove(player.getUniqueId());
    }
}
