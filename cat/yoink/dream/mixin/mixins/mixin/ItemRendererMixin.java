//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.mixin.mixins.mixin;

import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import cat.yoink.dream.mixin.mixins.accessor.IItemRenderer;

@Mixin({ ItemRenderer.class })
public class ItemRendererMixin implements IItemRenderer
{
    @Shadow
    private float equippedProgressMainHand;
    @Shadow
    private float equippedProgressOffHand;
    
    @Override
    public void setMainHand(final float mainHand) {
        this.equippedProgressMainHand = mainHand;
    }
    
    @Override
    public void setOffHand(final float offHand) {
        this.equippedProgressOffHand = offHand;
    }
}
