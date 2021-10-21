package main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    public static final String index = "§b[ §c루니 아조씨 §b]§f ";

    PluginDescriptionFile pdf = this.getDescription();

    @Override
    public void onEnable() {
        pdf.getCommands().keySet().forEach($->{
            Objects.requireNonNull(getCommand($)).setExecutor(new CMDHandler());
            Objects.requireNonNull(getCommand($)).setTabCompleter(new CMDHandler());
        });
        Bukkit.getPluginManager().registerEvents(new EventHandler(),this);
    }

    @Override
    public void onDisable() {

    }
}
