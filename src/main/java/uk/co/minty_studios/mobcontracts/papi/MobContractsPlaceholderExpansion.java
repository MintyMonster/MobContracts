package uk.co.minty_studios.mobcontracts.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import uk.co.minty_studios.mobcontracts.MobContracts;
import uk.co.minty_studios.mobcontracts.papi.placeholders.Placeholder;

import java.util.HashMap;
import java.util.Map;

public class MobContractsPlaceholderExpansion extends PlaceholderExpansion {

    private final MobContracts plugin;
    private Map<String, Placeholder> placeholders = new HashMap<>();

    public MobContractsPlaceholderExpansion(MobContracts plugin) {
        register();
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "mobcontracts";
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String id){

        if(player == null)
            return "";

        Placeholder placeholder = placeholders.get(id);

        if(placeholder != null)
            return placeholder.process(player, id);
        else
            return null;
    }

    public void registerPlaceholders(Placeholder placeholder){
        final Placeholder holders = placeholders.get(placeholder.getName());
        if(holders != null) {
            plugin.getLogger().info("Placeholder already registered: " + placeholder.getName());
            return;
        }

        placeholders.put(placeholder.getName(), placeholder);
    }
}
