package globalcode.forex.listeners;

import globalcode.forex.Main;
import globalcode.forex.utils.CUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    @EventHandler
    public void join(PlayerJoinEvent e){
        if(!Main.getPlugin().getConfig().getBoolean("greeting.enabled")) return;
        if(Main.getPlugin().getConfig().getString("greeting.message-type").equalsIgnoreCase("CHAT")){
            e.getPlayer().sendMessage(CUtil.fixColor(Main.getPlugin().getConfig().getString("greeting.message").replace("[prefix]", Main.getPlugin().getConfig().getString("prefix"))));
            return;
        }
        if(Main.getPlugin().getConfig().getString("greeting.message-type").equalsIgnoreCase("ACTIONBAR")){
            e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(CUtil.fixColor(Main.getPlugin().getConfig().getString("greeting.message").replace("[prefix]", Main.getPlugin().getConfig().getString("prefix")))));
            return;
        }
        if(Main.getPlugin().getConfig().getString("greeting.message-type").equalsIgnoreCase("TITLE")){
            String[] split = Main.getPlugin().getConfig().getString("greeting.message").split("[sub]");
            e.getPlayer().sendTitle(CUtil.fixColor(split[0].replace("[prefix]", Main.getPlugin().getConfig().getString("prefix"))), CUtil.fixColor(split[1].replace("[prefix]", Main.getPlugin().getConfig().getString("prefix"))), 15 ,20,15);
            return;
        }
    }
}
