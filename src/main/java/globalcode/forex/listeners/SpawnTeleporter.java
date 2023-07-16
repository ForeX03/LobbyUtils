package globalcode.forex.listeners;

import globalcode.forex.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SpawnTeleporter implements Listener {
    @EventHandler
    public void move(PlayerMoveEvent e){
        if(e.getTo().getBlockY()<0){
            FileConfiguration fc = Main.getPlugin().getConfig();
            if(!Main.getPlugin().getConfig().getBoolean("misc.teleport-from-void")) return;
            Location loc = new Location(e.getPlayer().getWorld(), fc.getDouble("spawn.x"), fc.getDouble("spawn.y"), fc.getDouble("spawn.z"), Float.parseFloat(fc.getString("spawn.yaw")), Float.parseFloat(fc.getString("spawn.pitch")));
            e.getPlayer().teleport(loc);
        }
    }

    @EventHandler
    public void teleport(PlayerJoinEvent e){
        FileConfiguration fc = Main.getPlugin().getConfig();
        for(Player player : Main.hiddenPlayers) player.hidePlayer(Main.getPlugin(), e.getPlayer());

        if(fc.getBoolean("misc.disable-join-message")) e.setJoinMessage(null);

        if(!fc.getBoolean("misc.teleport-on-join")) return;
        Location loc = new Location(e.getPlayer().getWorld(), fc.getDouble("spawn.x"), fc.getDouble("spawn.y"), fc.getDouble("spawn.z"), Float.parseFloat(fc.getString("spawn.yaw")), Float.parseFloat(fc.getString("spawn.pitch")));
        e.getPlayer().teleport(loc);
    }

    @EventHandler
    public void quit(PlayerQuitEvent e){
        if(Main.getPlugin().getConfig().getBoolean("misc.disable-quit-message")) e.setQuitMessage(null);
    }
}
