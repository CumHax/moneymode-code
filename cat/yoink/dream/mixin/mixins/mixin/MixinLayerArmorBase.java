//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.mixin.mixins.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.client.renderer.GlStateManager;
import cat.yoink.dream.impl.module.render.EnchantColor;
import cat.yoink.dream.api.module.ModuleManager;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ LayerArmorBase.class })
public class MixinLayerArmorBase
{
    @Redirect(method = { "renderEnchantedGlint" }, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GlStateManager.color(FFFF)V", ordinal = 1))
    private static void renderEnchantedGlint(final float red, final float green, final float blue, final float alpha) {
        GlStateManager.color(ModuleManager.getModule("EnchantColor").isEnabled() ? ((float)EnchantColor.getColor(1L, 1.0f).getRed()) : red, ModuleManager.getModule("EnchantColor").isEnabled() ? ((float)EnchantColor.getColor(1L, 1.0f).getGreen()) : green, ModuleManager.getModule("EnchantColor").isEnabled() ? ((float)EnchantColor.getColor(1L, 1.0f).getBlue()) : blue, ModuleManager.getModule("EnchantColor").isEnabled() ? ((float)EnchantColor.getColor(1L, 1.0f).getAlpha()) : alpha);
    }
}
