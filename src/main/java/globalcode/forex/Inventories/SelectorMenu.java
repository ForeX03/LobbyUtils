package globalcode.forex.Inventories;

import api.serverchecker.PingResponse;
import api.serverchecker.ServerPinger;
import globalcode.forex.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import globalcode.forex.utils.CUtil;
import globalcode.forex.utils.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

public class SelectorMenu {
    public static Inventory get()  {
        FileConfiguration config = Main.getPlugin().getConfig();
        Inventory inv = Bukkit.createInventory(null, config.getInt("gui.size")*9, CUtil.fixColor(config.getString("gui.name")));
        for(String s : config.getConfigurationSection("servers").getKeys(false)){
            String[] address = config.getString("servers."+s+".address").split(":");
            PingResponse pr = ServerPinger.fetchData(address[0], Integer.parseInt(address[1]), 1000);
            List<String> flore = new ArrayList<>();
            for(String lore : config.getStringList("servers."+s+".lore")){
                flore.add(lore.replace("[online]", pr.getOnlinePlayers()+"").replace("[maxonline]", pr.getMaxPlayers()+"").replace("[status]", pr.isOnline() ? config.getString("status.server-online") : config.getString("status.server-offline")));
            }
            ItemStack is = new ItemBuilder(Material.getMaterial(config.getString("servers."+s+".item"))).setTitle(CUtil.fixColor(config.getString("servers."+s+".menuname"))).addLores(CUtil.fixColor(flore)).build();
            ItemMeta im = is.getItemMeta();
            im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
            is.setItemMeta(im);

            inv.setItem(config.getInt("servers."+s+".slot"), is);
        }
        return inv;
    }
}
