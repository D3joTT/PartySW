package net.d3jott.partysw;

import net.d3jott.partysw.skywars.PlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PartySW extends JavaPlugin {

    @Override
    public void onEnable() {

        Bukkit.getPluginManager().registerEvents(new PlayerJoinEvent(), this);
    }
}
