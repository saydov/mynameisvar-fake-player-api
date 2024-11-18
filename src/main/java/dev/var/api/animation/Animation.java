package dev.var.api.animation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author mynameisvar <github@mynameisvar>
 */

@RequiredArgsConstructor
public enum Animation {
    SWING_MAIN_HAND(0),
    TAKE_DAMAGE(1),
    LEAVE_BED(2),
    SWING_OFF_HAND(3),
    CRITICAL_EFFECT(4),
    MAGIC_CRITICAL_EFFECT(5),
    SNEAK(-1),
    STOP_SNEAK(-2);

    @Getter
    private final int animationId;
}
