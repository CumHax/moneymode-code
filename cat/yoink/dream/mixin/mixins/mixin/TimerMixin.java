//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.mixin.mixins.mixin;

import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import cat.yoink.dream.mixin.mixins.accessor.ITimer;

@Mixin({ Timer.class })
public class TimerMixin implements ITimer
{
    @Shadow
    private float tickLength;
    
    @Override
    public float getTickLength() {
        return this.tickLength;
    }
    
    @Override
    public void setTickLength(final float tickLength) {
        this.tickLength = tickLength;
    }
}
