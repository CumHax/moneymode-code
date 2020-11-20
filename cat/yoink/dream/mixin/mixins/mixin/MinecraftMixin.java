//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.mixin.mixins.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import cat.cattyn.moneymod.money;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.util.Timer;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import cat.yoink.dream.mixin.mixins.accessor.IMinecraft;

@Mixin({ Minecraft.class })
public class MinecraftMixin implements IMinecraft
{
    @Shadow
    @Final
    private Timer timer;
    @Shadow
    public int rightClickDelayTimer;
    
    @Override
    public Timer getTimer() {
        return this.timer;
    }
    
    @Override
    public void setRightClickDelayTimer(final int i) {
        this.rightClickDelayTimer = i;
    }
    
    @Inject(method = { "shutdown()V" }, at = { @At("HEAD") })
    public void saveSettingsOnShutdown(final CallbackInfo ci) {
        money.configUtils.saveMods();
        System.out.println("Saved moneymod+2 mods!");
        money.configUtils.saveSettingsList();
        System.out.println("Saved moneymod+2 settings!");
        money.configUtils.saveBinds();
        System.out.println("Saved moneymod+2 binds!");
        money.configUtils.savePrefix();
    }
}
