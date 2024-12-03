package dev.var.entity.plugin;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class LocationUtils {

    public @NotNull Location fromString(@NotNull String location) {
        final String[] split = location.split(", ");
        if (split.length < 3) {
            throw new IllegalArgumentException("Invalid location format: " + location);
        }

        final World world = Bukkit.getWorld(split[0]);

        final double x = Double.parseDouble(split[1]);
        final double y = Double.parseDouble(split[2]);
        final double z = Double.parseDouble(split[3]);

        if (split.length > 4) {
            final float yaw = Float.parseFloat(split[4]);
            final float pitch = Float.parseFloat(split[5]);

            return new Location(world, x, y, z, yaw, pitch);
        }

        return new Location(world, x, y, z);
    }

}
