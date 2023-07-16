package globalcode.forex.command.commands;

import globalcode.forex.Main;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import globalcode.forex.command.CommandAPI;
import globalcode.forex.utils.CUtil;

import java.util.List;

public class SetSpawnCommand extends CommandAPI {
    public SetSpawnCommand(String name, String permission, String description, String usageMessage, List<String> aliases) {
        super(name, permission, description, usageMessage, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            Location l = p.getLocation();
            Main.getPlugin().getConfig().set("spawn.x", l.getX());
            Main.getPlugin().getConfig().set("spawn.y", l.getY());
            Main.getPlugin().getConfig().set("spawn.z", l.getZ());
            Main.getPlugin().getConfig().set("spawn.yaw", l.getYaw());
            Main.getPlugin().getConfig().set("spawn.pitch", l.getPitch());
            Main.getPlugin().saveConfig();
            Main.getPlugin().reloadConfig();
            p.sendMessage(CUtil.fixColor(Main.getPlugin().getConfig().getString("messages.setspawn").replace("[prefix]", Main.getPlugin().getConfig().getString("prefix"))));
        }
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        return null;
    }
}
