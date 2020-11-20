//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.event;

import net.minecraft.client.Minecraft;

public class MovementUtils
{
    static Minecraft mc;
    private static float roundedForward;
    private static float roundedStrafing;
    
    public static double calcMoveYaw(final float yawIn) {
        final float moveForward = MovementUtils.roundedForward;
        final float moveString = MovementUtils.roundedStrafing;
        float strafe = 90.0f * moveString;
        if (moveForward != 0.0f) {
            strafe *= moveForward * 0.5f;
        }
        else {
            strafe *= 1.0f;
        }
        float yaw = yawIn - strafe;
        if (moveForward < 0.0f) {
            yaw -= 180.0f;
        }
        else {
            yaw -= 0.0f;
        }
        return Math.toRadians(yaw);
    }
    
    private static float getRoundedMovementInput(Float input) {
        if (input > 0.0f) {
            input = 1.0f;
        }
        else if (input < 0.0f) {
            input = -1.0f;
        }
        else {
            input = 0.0f;
        }
        return input;
    }
    
    static {
        MovementUtils.mc = Minecraft.getMinecraft();
        MovementUtils.roundedForward = getRoundedMovementInput(Minecraft.getMinecraft().player.movementInput.moveForward);
        MovementUtils.roundedStrafing = getRoundedMovementInput(Minecraft.getMinecraft().player.movementInput.moveStrafe);
    }
}
