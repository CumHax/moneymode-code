//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Objects;
import net.minecraft.potion.Potion;
import cat.yoink.dream.api.module.Category;
import net.minecraft.potion.PotionEffect;
import cat.yoink.dream.api.module.Module;

public class Fullbright extends Module
{
    private boolean hasEffect;
    private final PotionEffect effect;
    
    public Fullbright(final String name, final String description, final Category category) {
        super(name, description, category);
        this.hasEffect = false;
        (this.effect = new PotionEffect((Potion)Objects.requireNonNull(Potion.getPotionById(16)))).setPotionDurationMax(true);
    }
    
    @Override
    public void onEnable() {
        if (this.mc.player == null) {
            return;
        }
        this.mc.player.addPotionEffect(this.effect);
        this.hasEffect = true;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null) {
            return;
        }
        if (!this.hasEffect) {
            this.mc.player.addPotionEffect(this.effect);
            this.hasEffect = true;
        }
    }
    
    @Override
    public void onDisable() {
        this.mc.player.removeActivePotionEffect(this.effect.getPotion());
        this.hasEffect = false;
    }
}
