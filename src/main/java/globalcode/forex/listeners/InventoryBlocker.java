package globalcode.forex.listeners;


import globalcode.forex.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import globalcode.forex.utils.CUtil;


public class InventoryBlocker implements Listener {
    @EventHandler
    public void click(InventoryClickEvent e){

        String title = e.getView().getTitle();
        Player p = (Player)e.getWhoClicked();
        FileConfiguration config = Main.getPlugin().getConfig();
        if(title.equals(CUtil.fixColor(config.getString("gui.name")))){
            e.setCancelled(true);
        }
        if (!Main.getPlugin().getConfig().getBoolean("misc.block-inventory")) return;
        if(!p.hasPermission("lobbyutils.inventory")) e.setCancelled(true);
    }
    @EventHandler
    public void drag(InventoryDragEvent e){
        if(!e.getWhoClicked().getType().equals(EntityType.PLAYER)) return;
        Player p = (Player)e.getWhoClicked();
        if (!Main.getPlugin().getConfig().getBoolean("misc.block-inventory")) return;
        if(!p.hasPermission("lobbyutils.inventory")) e.setCancelled(true);
    }
    @EventHandler
    public void pickup(EntityPickupItemEvent e){
        if(!e.getEntity().getType().equals(EntityType.PLAYER)) return;
        Player p = (Player)e.getEntity();
        if (!Main.getPlugin().getConfig().getBoolean("misc.block-pickup")) return;
        if(!p.hasPermission("lobbyutils.inventory.pickup")) e.setCancelled(true);
    }

    @EventHandler
    public void drop(PlayerDropItemEvent e){
        Player p = e.getPlayer();
        if (!Main.getPlugin().getConfig().getBoolean("misc.block-drop")) return;
        if(!p.hasPermission("lobbyutils.inventory.drop")) e.setCancelled(true);
    }
}
