package globalcode.forex.command.commands;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import globalcode.forex.Inventories.SelectorMenu;
import globalcode.forex.command.CommandAPI;

import java.util.List;

public class MenuCommand extends CommandAPI {
    public MenuCommand(String name, String permission, String description, String usageMessage, List<String> aliases) {
        super(name, permission, description, usageMessage, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            p.openInventory(SelectorMenu.get());
        }
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        return null;
    }
}
