// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.mixin.mixins.accessor;

import net.minecraft.util.Timer;

public interface IMinecraft
{
    Timer getTimer();
    
    void setRightClickDelayTimer(final int p0);
}
