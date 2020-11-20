//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;

public class ReverseStep extends Module
{
    public ReverseStep(final String name, final String description, final Category category) {
        super(name, description, category);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        try {
            if (this.mc.player == null) {
                return;
            }
            if (!this.mc.player.onGround || this.mc.player.isOnLadder() || this.mc.player.isInWater() || this.mc.player.isInLava() || this.mc.player.movementInput.jump || this.mc.player.noClip) {
                return;
            }
            if (this.mc.player.moveForward == 0.0f && this.mc.player.moveStrafing == 0.0f) {
                return;
            }
            this.mc.player.motionY = -1.0;
        }
        catch (Exception ex) {}
    }
}
