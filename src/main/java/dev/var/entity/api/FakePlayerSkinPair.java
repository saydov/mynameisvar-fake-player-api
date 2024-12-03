package dev.var.entity.api;

import lombok.*;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode
@ToString
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FakePlayerSkinPair<T, S> {

    public static @NotNull <T, S> FakePlayerSkinPair<T, S> create(final @NotNull T texture,
                                                                  final @NotNull S signature) {
        return new FakePlayerSkinPair<>(texture, signature);
    }

    private final T texture;

    private final S signature;

}
