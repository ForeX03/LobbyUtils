package globalcode.forex.listeners;

import globalcode.forex.Main;
import globalcode.forex.utils.CUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatBlocker implements Listener {
    @EventHandler
    public void chat(AsyncPlayerChatEvent e){
        if (!Main.getPlugin().getConfig().getBoolean("misc.block-chat")) return;
        if(e.getPlayer().hasPermission("lobbyutils.chat")) return;
        e.setCancelled(true);
        e.getPlayer().sendMessage(CUtil.fixColor(Main.getPlugin().getConfig().getString("misc.chat-cancelled").replace("[prefix]", Main.getPlugin().getConfig().getString("prefix"))));
    }
}
