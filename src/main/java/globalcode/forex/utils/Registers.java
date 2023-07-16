package globalcode.forex.utils;

import globalcode.forex.Main;
import globalcode.forex.command.commands.GLobbyCommand;
import globalcode.forex.command.commands.MenuCommand;
import globalcode.forex.command.commands.SetSpawnCommand;
import globalcode.forex.listeners.*;
import globalcode.forex.listeners.BreakBlocker;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;

public interface Registers {
    @SneakyThrows
    default void registerCommands(Main plugin) {
        final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        bukkitCommandMap.setAccessible(true);
        final CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

        Arrays.asList(
                new GLobbyCommand("globby", "lobbyutils.reload", "Komenda administracyjna", "/globby reload", Collections.singletonList("globallobby")),
                new SetSpawnCommand("setspawn", "lobbyutils.setspawn", "Ustawia spawn", "/setspawn", Collections.singletonList("ustawspawn")),
                new MenuCommand(plugin.getConfig().getString("misc.open-menu-command"), null, "Otwiera menu z trybami", plugin.getConfig().getString("misc.open-menu-command"), plugin.getConfig().getStringList("misc.open-menu-command-aliases"))
        ).forEach(commands -> commandMap.register("", commands));
    }

    default void registerListeners(Main plugin){
        Bukkit.getPluginManager().registerEvents(new SaturationChangeEvent(), plugin);
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), plugin);
        Bukkit.getPluginManager().registerEvents(new SpawnTeleporter(), plugin);
        Bukkit.getPluginManager().registerEvents(new InventoryBlocker(), plugin);
        Bukkit.getPluginManager().registerEvents(new ChatBlocker(), plugin);
        Bukkit.getPluginManager().registerEvents(new BreakBlocker(), plugin);
        Bukkit.getPluginManager().registerEvents(new DamageBlocker(), plugin);
        Bukkit.getPluginManager().registerEvents(new WeatherBlocker(), plugin);
        Bukkit.getPluginManager().registerEvents(new InventorySetter(), plugin);
        Bukkit.getPluginManager().registerEvents(new InventoryClickHandler(), plugin);
    }
}
