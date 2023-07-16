package globalcode.forex.tasks;

import globalcode.forex.Main;
import globalcode.forex.utils.CUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class MessengerTask extends BukkitRunnable {
    Main plugin;
    Integer i;

    Boolean enabled;
    public MessengerTask(Main main) {
        plugin = main;
        enabled = main.getConfig().getBoolean("auto-message.enabled");
        if(enabled) {
            start();
        }
    }
    public void enable(){
        enabled = true;
        start();
    }
    public void disable(){
        enabled = false;
        cancel();
    }
    private void start(){
        runTaskTimerAsynchronously(plugin, 0, plugin.getConfig().getLong("auto-message.interval"));
        i = plugin.getConfig().getStringList("auto-message.messages").size();
    }

    @Override
    public void run() {
        if(i==0) i = plugin.getConfig().getStringList("auto-message.messages").size();
        Bukkit.broadcastMessage(CUtil.fixColor(plugin.getConfig().getStringList("auto-message.messages").get(i-1).replace("[prefix]", Main.getPlugin().getConfig().getString("prefix"))));
        i--;
    }
}
