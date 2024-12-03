package dev.var.entity.api;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class FakePlayerListener implements Listener {

    public static FakePlayerListener create(final @NotNull JavaPlugin javaPlugin,
                                            final @NotNull FakePlayerApi fakePlayerApi) {
        return new FakePlayerListener(javaPlugin, fakePlayerApi);
    }

    private final JavaPlugin javaPlugin;
    private final FakePlayerApi fakePlayerApi;

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onAsyncJoin(final PlayerJoinEvent event) {
        javaPlugin.getServer().getScheduler()
                .runTaskAsynchronously(javaPlugin, () -> fakePlayerApi.getFakePlayers()
                        .stream()
                        .filter(FakeEntity::isPublicStatus)
                        .forEach(fakePlayer -> fakePlayer.addViewer(event.getPlayer())));
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onAsyncQuit(final PlayerQuitEvent event) {
        fakePlayerApi.getFakePlayers()
                .stream()
                .filter(FakeEntity::isPublicStatus)
                .forEach(fakePlayer -> fakePlayer.removeViewer(event.getPlayer()));
    }

}
