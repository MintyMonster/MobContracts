package uk.co.minty_studios.mobcontracts.gui.handler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.co.minty_studios.mobcontracts.utils.CreateCustomGuiItem;

import java.util.ArrayList;
import java.util.List;

public abstract class PaginatedGui extends Gui{

    protected int page = 0;
    protected int maxItemsPerPage = 45;

    public PaginatedGui(GuiUtil guiUtil){
        super(guiUtil);
    }
    // 46 - 54
    // 51 49

    public void addBottomRow(){

        // Buttons
        ItemStack next = new ItemStack(Material.PAPER, 1);
        ItemMeta nextmeta = next.getItemMeta();
        nextmeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Next page");
        next.setItemMeta(nextmeta);
        inventory.setItem(50, next);

        ItemStack back = new ItemStack(Material.PAPER, 1);
        ItemMeta backmeta = back.getItemMeta();
        backmeta.setDisplayName(ChatColor.GRAY + "" + ChatColor.BOLD + "Previous page");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "[Back to main menu]");
        if(page == 0){
            backmeta.setLore(lore);
        }
        back.setItemMeta(backmeta);
        inventory.setItem(48, back);

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closemeta = close.getItemMeta();
        closemeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Close");
        close.setItemMeta(closemeta);
        inventory.setItem(49, close);

        ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = filler.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8"));
        filler.setItemMeta(meta);

        // add back to main menu button

        // Bottom row fillers
        for(int i = 45; i < 54; i++) {
            if(inventory.getItem(i) == null){
                inventory.setItem(i, filler);
            }
        }
    }
}
