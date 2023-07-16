package globalcode.forex;

import globalcode.forex.tasks.MessengerTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import globalcode.forex.utils.Registers;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin implements Registers {

    private static Main plugin;
    private static MessengerTask mtask;
    public static List<Player> hiddenPlayers = new ArrayList<>();
    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        if(getConfig().getBoolean("misc.block-weather-change")) {
            Bukkit.getWorld(getConfig().getString("misc.world")).setThundering(false);
            Bukkit.getWorld(getConfig().getString("misc.world")).setStorm(false);
        }
        registerCommands(this);
        registerListeners(this);
        new MessengerTask(this);
    }
    public void onDisable(){
        Bukkit.getScheduler().cancelTasks(this);
    }

    public static Main getPlugin() {
        return plugin;
    }

    public static MessengerTask getMessenger(){
        return mtask;
    }

}
