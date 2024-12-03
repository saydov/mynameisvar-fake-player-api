package dev.var.entity.api.utils;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
@Slf4j
public class NmsUtils {

    private static final String SERVER_VERSION;

    private static AtomicInteger ENTITY_ID;

    static {
        SERVER_VERSION = Bukkit.getServer()
                .getClass().getName().split("\\.")[3];

        try {
            ENTITY_ID = new AtomicInteger((int) getClazz("Entity")
                    .getDeclaredField("b") // entity count
                    .get(null));
        } catch (final IllegalAccessException | NoSuchFieldException e) {
            log.error("Failed to initialize ENTITY_ID", e);
        }
    }

    public static int nextEntityId() {
        return ENTITY_ID.incrementAndGet();
    }

    @SneakyThrows
    public @NotNull Class<?> getClazz(final @NonNull String name) {
        return Class.forName("net.minecraft.server." + SERVER_VERSION + "." + name);
    }

}
