package globalcode.forex.listeners;

import globalcode.forex.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherBlocker implements Listener {

    @EventHandler
    public void weather(WeatherChangeEvent e){
        if(Main.getPlugin().getConfig().getBoolean("misc.block-weather-change")) e.setCancelled(true);
    }
}
