//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.event;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderEvent extends Event
{
    private final Tessellator tessellator;
    private final Vec3d renderPos;
    private final float partialTicks;
    
    public RenderEvent(final Tessellator tessellator, final Vec3d renderPos, final float ticks) {
        this.tessellator = tessellator;
        this.renderPos = renderPos;
        this.partialTicks = ticks;
    }
    
    public Tessellator getTessellator() {
        return this.tessellator;
    }
    
    public BufferBuilder getBuffer() {
        return this.tessellator.getBuffer();
    }
    
    public Vec3d getRenderPos() {
        return this.renderPos;
    }
    
    public void setTranslation(final Vec3d translation) {
        this.getBuffer().setTranslation(-translation.x, -translation.y, -translation.z);
    }
    
    public void resetTranslation() {
        this.setTranslation(this.renderPos);
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
