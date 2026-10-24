package pl.sektor.core3;

import org.bukkit.plugin.java.JavaPlugin;

public final class Core3 extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new InfoListener(this), this);
    }
}
