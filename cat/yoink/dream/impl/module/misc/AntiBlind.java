//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;

public class AntiBlind extends Module
{
    public AntiBlind(final String name, final String description, final Category category) {
        super(name, description, category);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null) {
            return;
        }
        if (this.mc.player.isPotionActive(Potion.getPotionById(9))) {
            this.mc.player.removeActivePotionEffect(Potion.getPotionById(9));
        }
        if (this.mc.player.isPotionActive(Potion.getPotionById(15))) {
            this.mc.player.removeActivePotionEffect(Potion.getPotionById(15));
        }
    }
}
