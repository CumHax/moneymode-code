//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.mixin.mixins.mixin;

import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import cat.yoink.dream.mixin.mixins.accessor.IRenderManager;

@Mixin({ RenderManager.class })
public class RenderManagerMixin implements IRenderManager
{
    @Shadow
    private double renderPosX;
    @Shadow
    private double renderPosY;
    @Shadow
    private double renderPosZ;
    
    @Override
    public double getRenderPosX() {
        return this.renderPosX;
    }
    
    @Override
    public double getRenderPosY() {
        return this.renderPosY;
    }
    
    @Override
    public double getRenderPosZ() {
        return this.renderPosZ;
    }
}
