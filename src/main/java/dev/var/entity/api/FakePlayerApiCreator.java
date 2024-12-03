package dev.var.entity.api;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

@UtilityClass
public class FakePlayerApiCreator {

    public @NotNull FakePlayerApi apiWithFastHashSet(final @NotNull JavaPlugin javaPlugin) {
        return SimpleFakePlayerApi.create(javaPlugin, new ObjectOpenHashSet<>());
    }

    public @NotNull FakePlayerApi apiWithHashSet(final @NotNull JavaPlugin javaPlugin) {
        return SimpleFakePlayerApi.create(javaPlugin, new HashSet<>());
    }

}
