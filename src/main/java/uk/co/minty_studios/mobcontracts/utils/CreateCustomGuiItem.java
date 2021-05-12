package uk.co.minty_studios.mobcontracts.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import uk.co.minty_studios.mobcontracts.MobContracts;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreateCustomGuiItem {

    private final MobContracts plugin;

    public CreateCustomGuiItem(MobContracts plugin) {
        this.plugin = plugin;
    }

    public ItemStack getCustomSkull(final String displayName, String texture, final String... lore){
        texture = "http://textures.minecraft.net/texture/" + texture;

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);

        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        skullMeta.setLore(Arrays.stream(lore).map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList()));
        //skullMeta.setLore(Arrays.asList(lore));

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] data = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", texture).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(data)));
        Field profileField = null;

        try{
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        assert profileField != null;
        profileField.setAccessible(true);

        try{
            profileField.set(skullMeta, profile);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        skull.setItemMeta(skullMeta);
        return skull;
    }

    public ItemStack getCustomItem(final Material material, final String displayName, final String... lore){
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        meta.setLore(Arrays.stream(lore).map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList()));
        item.setItemMeta(meta);

        return item;
    }

    // create singular method
    public ItemStack getPlayerHead(final UUID uuid, final String... lore){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        if(plugin.getServer().getPlayer(uuid) != null){
            meta.setOwningPlayer(plugin.getServer().getPlayer(uuid));
            meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + plugin.getServer().getPlayer(uuid).getDisplayName());
        }else{
            meta.setOwningPlayer(plugin.getServer().getOfflinePlayer(uuid));
            meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + plugin.getServer().getOfflinePlayer(uuid).getName());
        }

        meta.setLore(Arrays.stream(lore).map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList()));
        skull.setItemMeta(meta);

        return skull;
    }

    public enum MobType {
        EVOKER("d954135dc82213978db478778ae1213591b93d228d36dd54f1ea1da48e7cba6"),
        VINDICATOR("6deaec344ab095b48cead7527f7dee61b063ff791f76a8fa76642c8676e2173"),
        PILLAGER("4aee6bb37cbfc92b0d86db5ada4790c64ff4468d68b84942fde04405e8ef5333"),
        RAVAGER("1cb9f139f9489d86e410a06d8cbc670c8028137508e3e4bef612fe32edd60193"),
        VEX("c2ec5a516617ff1573cd2f9d5f3969f56d5575c4ff4efefabd2a18dc7ab98cd"),
        ENDERMITE("5bc7b9d36fb92b6bf292be73d32c6c5b0ecc25b44323a541fae1f1e67e393a3e"),
        GUARDIAN("c25af966a326f9d98466a7bf8582ca4da6453de271b3bc9e59f57a99b63511c6"),
        ELDER_GUARDIAN("4340a268f25fd5cc276ca147a8446b2630a55867a2349f7ca107c26eb58991"),
        HUSK("d674c63c8db5f4ca628d69a3b1f8a36e29d8fd775e1a6bdb6cabb4be4db121"),
        STRAY("2c5097916bc0565d30601c0eebfeb287277a34e867b4ea43c63819d53e89ede7"),
        PHANTOM("7e95153ec23284b283f00d19d29756f244313a061b70ac03b97d236ee57bd982"),
        BLAZE("b78ef2e4cf2c41a2d14bfde9caff10219f5b1bf5b35a49eb51c6467882cb5f0"),
        CREEPER("f4254838c33ea227ffca223dddaabfe0b0215f70da649e944477f44370ca6952"),
        GHAST("7a8b714d32d7f6cf8b37e221b758b9c599ff76667c7cd45bbc49c5ef19858646"),
        MAGMA_CUBE("38957d5023c937c4c41aa2412d43410bda23cf79a9f6ab36b76fef2d7c429"),
        SILVERFISH("da91dab8391af5fda54acd2c0b18fbd819b865e1a8f1d623813fa761e924540"),
        SKELETON("301268e9c492da1f0d88271cb492a4b302395f515a7bbf77f4a20b95fc02eb2"),
        SLIME("a20e84d32d1e9c919d3fdbb53f2b37ba274c121c57b2810e5a472f40dacf004f"),
        ZOMBIE("56fc854bb84cf4b7697297973e02b79bc10698460b51a639c60e5e417734e11"),
        ZOMBIE_VILLAGER("e5e08a8776c1764c3fe6a6ddd412dfcb87f41331dad479ac96c21df4bf3ac89c"),
        DROWNED("c84df79c49104b198cdad6d99fd0d0bcf1531c92d4ab6269e40b7d3cbbb8e98c"),
        WITHER_SKELETON("7953b6c68448e7e6b6bf8fb273d7203acd8e1be19e81481ead51f45de59a8"),
        WITCH("20e13d18474fc94ed55aeb7069566e4687d773dac16f4c3f8722fc95bf9f2dfa"),
        HOGLIN("9bb9bc0f01dbd762a08d9e77c08069ed7c95364aa30ca1072208561b730e8d75"),
        ZOGLIN("e67e18602e03035ad68967ce090235d8996663fb9ea47578d3a7ebbc42a5ccf9"),
        PIGLIN_BRUTE("3e300e9027349c4907497438bac29e3a4c87a848c50b34c21242727b57f4e1cf"),
        NONE("eb7af9e4411217c7de9c60acbd3c3fd6519783332a1b3bc56fbfce90721ef35");

        private String base;

        MobType(String base){
            this.base = base;
        }

        public String getBase(){
            return this.base;
        }
    }
}
