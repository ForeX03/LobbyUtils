package globalcode.forex.command.commands;

import globalcode.forex.Main;
import globalcode.forex.tasks.MessengerTask;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import globalcode.forex.command.CommandAPI;
import globalcode.forex.utils.CUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GLobbyCommand extends CommandAPI {

    public GLobbyCommand(String name, String permission, String description, String usageMessage, List<String> aliases) {
        super(name, permission, description, usageMessage, aliases);
    }
    private static final List<String> arguments = Collections.singletonList("reload");
    @Override
    public void execute(CommandSender sender, String[] args) {
        if((args.length!=1) || (!args[0].equalsIgnoreCase("reload"))) {
            sendUsage(sender);
            return;
        }
        Main.getPlugin().reloadConfig();
        if(Main.getPlugin().getConfig().getBoolean("auto-message.enabled")){
            Main.getMessenger().enable();
        } else {
            Main.getMessenger().disable();
        }
        sender.sendMessage(CUtil.fixColor(Main.getPlugin().getConfig().getString("messages.reload").replace("[prefix]", Main.getPlugin().getConfig().getString("prefix"))));
    }

    @Override
    public List<String> tab(@NonNull Player player, @NonNull String[] args) {
        ArrayList<String> result = new ArrayList<>();
        if (args.length == 1) {
            for (String arg : arguments) if (arg.startsWith(args[0].toLowerCase())) result.add(arg);
            return result;
        }
        return result;
    }
}
