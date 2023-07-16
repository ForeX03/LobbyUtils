package globalcode.forex.listeners;

import globalcode.forex.Main;
import globalcode.forex.utils.CUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakBlocker implements Listener {

    @EventHandler
    public void blockbreak(BlockBreakEvent e){
        if (!Main.getPlugin().getConfig().getBoolean("misc.block-breaking")) return;
        if(e.getPlayer().hasPermission("lobbyutils.blockbreak")) return;
        e.setCancelled(true);
        e.getPlayer().sendMessage(CUtil.fixColor(Main.getPlugin().getConfig().getString("misc.breaking-cancelled").replace("[prefix]", Main.getPlugin().getConfig().getString("prefix"))));
    }
}
