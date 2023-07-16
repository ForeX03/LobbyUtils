package globalcode.forex.listeners;

import globalcode.forex.Main;
import globalcode.forex.utils.CUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageBlocker implements Listener {

    @EventHandler
    public void damage(EntityDamageByEntityEvent e){
        if(e.getDamager().getType().equals(EntityType.PLAYER)) {
            Player p = (Player) e.getDamager();
            if (!Main.getPlugin().getConfig().getBoolean("misc.disable-damage-by-entity")) return;
            if(p.hasPermission("lobbyutils.damage")) return;
            e.setCancelled(true);
            p.sendMessage(CUtil.fixColor(Main.getPlugin().getConfig().getString("misc.damage-cancelled").replace("[prefix]", Main.getPlugin().getConfig().getString("prefix"))));
            return;
        }
        if (!Main.getPlugin().getConfig().getBoolean("misc.disable-damage-by-environment")) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void damage(EntityDamageEvent e){
        if(e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) return;
        if (!Main.getPlugin().getConfig().getBoolean("misc.disable-damage-by-environment")) return;
        e.setCancelled(true);
    }
}
