//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.mixin.mixins.mixin;

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import cat.yoink.dream.api.module.ModuleManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInputFromOptions;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.util.MovementInput;

@Mixin({ MovementInputFromOptions.class })
public class MovementInputFromOptionsMixin extends MovementInput
{
    @Redirect(method = { "updatePlayerMoveState" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"))
    public boolean isKeyPressed(final KeyBinding keyBinding) {
        if (ModuleManager.getModule("InventoryMove").isEnabled() && Minecraft.getMinecraft().currentScreen instanceof GuiContainer) {
            return Keyboard.isKeyDown(keyBinding.getKeyCode());
        }
        return keyBinding.isKeyDown();
    }
    
    @Inject(method = { "updatePlayerMoveState" }, at = { @At("RETURN") }, cancellable = true)
    public void updatePlayerMoveStateReturn(final CallbackInfo callback) {
        if (ModuleManager.getModule("AutoWalk").isEnabled()) {
            this.moveForward = 1.0f;
        }
        if (ModuleManager.getModule("AutoSwim").isEnabled() && (Minecraft.getMinecraft().player.isInLava() || Minecraft.getMinecraft().player.isInWater())) {
            this.jump = true;
        }
    }
}
