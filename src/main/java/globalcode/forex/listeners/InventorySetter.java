package globalcode.forex.listeners;

import globalcode.forex.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import globalcode.forex.utils.CUtil;
import globalcode.forex.utils.ItemBuilder;
import globalcode.forex.utils.enums.Fly;
import globalcode.forex.utils.enums.Visibility;

public class InventorySetter implements Listener {

    @EventHandler
    public void inv(PlayerJoinEvent e){
        Player p = e.getPlayer();
        FileConfiguration cf = Main.getPlugin().getConfig();
        e.getPlayer().getInventory().clear();
        if(cf.getBoolean("items.selector.enabled")){
            ItemBuilder selector_builder = new ItemBuilder(Material.getMaterial(cf.getString("items.selector.item")),1);
            selector_builder.setTitle(CUtil.fixColor(cf.getString("items.selector.name")));
            selector_builder.addLores(CUtil.fixColor(cf.getStringList("items.selector.lore")));
            ItemStack selector = selector_builder.build();
            e.getPlayer().getInventory().setItem(cf.getInt("items.selector.slot"), selector);
        }
        if(cf.getBoolean("items.visibility-toggler.enabled")){
            ItemBuilder visibility_toggler_builder = new ItemBuilder(Material.getMaterial(cf.getString("items.visibility-toggler.item")), 1);
            Visibility visibility_default = Visibility.valueOf(cf.getString("items.visibility-toggler.default"));
            if(visibility_default == Visibility.HIDE){
                Main.hiddenPlayers.add(p);
                for(Player p1 : Bukkit.getOnlinePlayers()){
                    p.hidePlayer(Main.getPlugin(), p1);
                }
            }
            visibility_toggler_builder.setTitle(CUtil.fixColor(cf.getString("items.visibility-toggler.name").replace("[status]", cf.getString("items.visibility-toggler.status." + visibility_default.name().toLowerCase()))));
            visibility_toggler_builder.addLores(CUtil.fixColor(cf.getStringList("items.visibility-toggler.lore")));
            ItemStack visibility_toggler = visibility_toggler_builder.build();
            e.getPlayer().getInventory().setItem(cf.getInt("items.visibility-toggler.slot"), visibility_toggler);
        }
        if(cf.getBoolean("items.fly-toggler.enabled")) {
            ItemBuilder fly_toggler_builder = new ItemBuilder(Material.getMaterial(cf.getString("items.fly-toggler.item")), 1);
            Fly fly_default = Fly.valueOf(cf.getString("items.fly-toggler.default"));
            p.setAllowFlight(fly_default == Fly.ENABLED);
            fly_toggler_builder.setTitle(CUtil.fixColor(cf.getString("items.fly-toggler.name").replace("[status]", cf.getString("items.fly-toggler.status." + fly_default.name().toLowerCase()))));
            fly_toggler_builder.addLores(CUtil.fixColor(cf.getStringList("items.fly-toggler.lore")));
            ItemStack fly_toggler = fly_toggler_builder.build();
            p.getInventory().setItem(cf.getInt("items.fly-toggler.slot"), fly_toggler);
        }
        e.getPlayer().getInventory().setHeldItemSlot(cf.getInt("misc.default-item-slot"));
    }
}
