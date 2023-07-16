package globalcode.forex.listeners;

import api.serverchecker.PingResponse;
import api.serverchecker.ServerPinger;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import globalcode.forex.Inventories.SelectorMenu;
import globalcode.forex.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import globalcode.forex.utils.CUtil;
import globalcode.forex.utils.enums.Fly;
import globalcode.forex.utils.enums.Visibility;

public class InventoryClickHandler implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e){
        String title = e.getView().getTitle();
        Player p = (Player)e.getWhoClicked();
        FileConfiguration config = Main.getPlugin().getConfig();
        if(title.equals(CUtil.fixColor(config.getString("gui.name")))){
            for(String s : config.getConfigurationSection("servers").getKeys(false)){
                if(e.getSlot()==config.getInt("servers."+s+".slot")){
                    String[] address = config.getString("servers."+s+".address").split(":");
                    PingResponse pr = ServerPinger.fetchData(address[0], Integer.parseInt(address[1]), 1000);
                    p.sendMessage(CUtil.fixColor(config.getString("messages.connect").replace("[prefix]", config.getString("prefix")).replace("[serwer]", config.getString("servers."+s+".displayname"))));
                    if(!pr.isOnline()){
                        p.sendMessage(CUtil.fixColor(config.getString("messages.offline").replace("[prefix]", config.getString("prefix"))));
                    } else {
                        ByteArrayDataOutput out = ByteStreams.newDataOutput();
                        out.writeUTF("Connect");
                        out.writeUTF(config.getString("servers." + s + ".bungee"));
                        p.sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
                    }
                    p.closeInventory();
                }
            }
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void interact(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        FileConfiguration config = Main.getPlugin().getConfig();
        int slot = p.getInventory().getHeldItemSlot();
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(config.getBoolean("items.selector.enabled")){
                if(config.getInt("items.selector.slot")==slot){
                    p.openInventory(SelectorMenu.get());
                    e.setCancelled(true);
                }
            }
            if(config.getBoolean("items.fly-toggler.enabled")){
                if(config.getInt("items.fly-toggler.slot")==slot){
                    p.setAllowFlight(!p.getAllowFlight());
                    Fly fly = p.getAllowFlight() ? Fly.ENABLED : Fly.DISABLED;
                    ItemStack i = p.getInventory().getItemInMainHand();
                    ItemMeta im = i.getItemMeta();
                    im.setDisplayName(CUtil.fixColor(config.getString("items.fly-toggler.name").replace("[status]", config.getString("items.fly-toggler.status." + fly.name().toLowerCase()))));
                    i.setItemMeta(im);
                    p.getInventory().setItemInMainHand(i);
                    e.setCancelled(true);
                }
            }
            if(config.getBoolean("items.visibility-toggler.enabled")){
                if(config.getInt("items.visibility-toggler.slot")==slot){
                    ItemStack i = p.getInventory().getItemInMainHand();
                    ItemMeta im = i.getItemMeta();
                    Visibility visibility = togglePlayers(p, Main.hiddenPlayers.contains(p));
                    im.setDisplayName(CUtil.fixColor(config.getString("items.visibility-toggler.name").replace("[status]", config.getString("items.visibility-toggler.status."+visibility.name().toLowerCase()))));
                    i.setItemMeta(im);
                    p.getInventory().setItemInMainHand(i);
                    e.setCancelled(true);
                }
            }
        }
    }
    public static Visibility togglePlayers(Player p, Boolean hidden){
        if(hidden){
            Main.hiddenPlayers.remove(p);
            for(Player p1 : Bukkit.getOnlinePlayers()){
                p.showPlayer(Main.getPlugin(), p1);
            }
            return Visibility.SHOW;
        }
        Main.hiddenPlayers.add(p);
        for(Player p1 : Bukkit.getOnlinePlayers()){
            p.hidePlayer(Main.getPlugin(), p1);
        }
        return Visibility.HIDE;
    }
}
