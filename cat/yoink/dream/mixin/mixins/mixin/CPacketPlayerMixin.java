//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.mixin.mixins.mixin;

import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import cat.yoink.dream.mixin.mixins.accessor.ICPacketPlayer;

@Mixin({ CPacketPlayer.class })
public abstract class CPacketPlayerMixin implements ICPacketPlayer
{
    @Shadow
    public double x;
    @Shadow
    public double y;
    @Shadow
    public double z;
    @Shadow
    public float yaw;
    @Shadow
    public float pitch;
    @Shadow
    public boolean onGround;
    
    @Override
    public void setX(final double x) {
        this.x = x;
    }
    
    @Override
    public void setY(final double y) {
        this.y = y;
    }
    
    @Override
    public void setZ(final double z) {
        this.z = z;
    }
    
    @Override
    public void setYaw(final float yaw) {
        this.yaw = yaw;
    }
    
    @Override
    public void setPitch(final float pitch) {
        this.pitch = pitch;
    }
    
    @Override
    public void setOnGround(final boolean onGround) {
        this.onGround = onGround;
    }
}
