package globalcode.forex.command;


import globalcode.forex.Main;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import globalcode.forex.utils.CUtil;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class CommandAPI extends BukkitCommand {
    private final String permission;
    private final FileConfiguration pluginConfig = Main.getPlugin().getConfig();

    @SneakyThrows
    public CommandAPI(@NotNull String name, String permission, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
        this.permission = permission;
        this.setPermission(permission);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        execute(commandSender, strings);
        return false;
    }

    @Override
    public @NonNull List<String> tabComplete(@NonNull CommandSender sender, @NonNull String label, @NotNull String[] args) {
        List<String> completions = tab((Player) sender, args);
        if (completions == null) return new ArrayList<>();
        else return completions;
    }
    public void sendUsage(CommandSender cs) {
        cs.sendMessage(CUtil.fixColor(" &8*>> &7Poprawne użycie: &6"+getUsage()));
    }
    public abstract void execute(CommandSender sender, String[] args);
    public abstract List<String> tab(@NonNull Player player, @NonNull String[] args);

}
