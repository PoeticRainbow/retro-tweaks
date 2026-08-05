package io.github.poeticrainbow.retrotweaks.util;

import net.minecraft.client.Minecraft;

public class BillboardHelper {
    public static float billboardYawRadians() {
        return (float) Math.toRadians(billboardYawDegrees());
    }

    public static float billboardYawDegrees() {
        if (Minecraft.getInstance().getCameraEntity() != null) {
            return 180f - Minecraft.getInstance().getCameraEntity().getYHeadRot();
        }
        return 0f;
    }
}
