package pl.sektor.core3;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/** Podmienia /pl, /plugins i /pluginy na ladna wiadomosc o serwerze. */
public final class InfoListener implements Listener {

    private static final Set<String> LABELS = Set.of("pl", "plugins", "pluginy");
    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();

    private final Core3 plugin;

    public InfoListener(Core3 plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String msg = e.getMessage();
        if (msg.length() < 2 || msg.charAt(0) != '/') return;

        String label = msg.substring(1).split(" ", 2)[0].toLowerCase(Locale.ROOT);
        boolean prefixed = false;
        int colon = label.indexOf(':');
        if (colon >= 0) {
            label = label.substring(colon + 1);
            prefixed = true;
        }
        if (!LABELS.contains(label)) return;

        // Admin moze uzyc np. /bukkit:pl, zeby zobaczyc prawdziwa liste pluginow.
        if (prefixed && e.getPlayer().hasPermission("sektor.admin")) return;

        e.setCancelled(true);
        send(e.getPlayer());
    }

    private void send(Player p) {
        String server = plugin.getConfig().getString("info.server", "duckmc.pl");
        String discord = plugin.getConfig().getString("info.discord", "https://discord.gg/PfuQfkbNg");
        List<String> lines = plugin.getConfig().getStringList("info.lines");

        for (String raw : lines) {
            boolean hasLink = raw.contains("{discord}");
            String text = raw.replace("{server}", server).replace("{discord}", discord);
            Component c = LEGACY.deserialize(text).decoration(TextDecoration.ITALIC, false);
            if (hasLink) {
                c = c.clickEvent(ClickEvent.openUrl(discord))
                     .hoverEvent(HoverEvent.showText(Component.text("Kliknij, aby wejsc na Discord")));
            }
            p.sendMessage(c);
        }
    }
}
