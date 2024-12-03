package dev.var.entity.api;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import dev.var.entity.api.utils.NmsUtils;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public abstract class FakeEntityBase implements FakeEntity {

    private final UUID uniqueId;
    private final int entityId;

    private final Location location;
    private final WrappedDataWatcher dataWatcher;

    private final Set<Player> viewers;

    private boolean publicStatus;

    @Setter @Accessors(chain = true)
    private @Nullable String displayName;

    protected FakeEntityBase(final @NotNull Location location) {
        this(location, true);
    }

    protected FakeEntityBase(final @NotNull Location location, final boolean publicStatus) {
        this.uniqueId = UUID.randomUUID();
        this.entityId = NmsUtils.nextEntityId();

        this.location = location;
        this.publicStatus = publicStatus;

        this.dataWatcher = new WrappedDataWatcher();
        this.viewers = new ObjectOpenHashSet<>();
    }

    @Override
    public void addViewer(@NotNull Player player) {
        if (viewers.add(player)) {
            spawn(player);
        }
    }

    @Override
    public void removeViewer(@NotNull Player player) {
        if (viewers.remove(player)) {
            remove(player);
        }
    }

    @Override
    public @NotNull FakeEntity setPublicStatus(boolean status) {
        this.publicStatus = status;

        if (status) {
            viewers.forEach(this::spawn);
        } else {
            viewers.forEach(this::remove);
        }

        return this;
    }

}
