package globalcode.forex.listeners;

import globalcode.forex.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class SaturationChangeEvent implements Listener {
    @EventHandler
    public void saturation(FoodLevelChangeEvent e){
        if(!Main.getPlugin().getConfig().getBoolean("disable-saturation")) return;
        e.setCancelled(true);
    }
}
