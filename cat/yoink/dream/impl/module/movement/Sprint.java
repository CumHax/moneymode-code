//Deobfuscated with CENSORED

// 
// Decompiled by Procyon v0.5.36
// 

package cat.yoink.dream.impl.module.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import cat.yoink.dream.api.module.Category;
import cat.yoink.dream.api.module.Module;

public class Sprint extends Module
{
    public Sprint(final String name, final String description, final Category category) {
        super(name, description, category);
    }
    
    @SubscribeEvent
    public void onPlayerTick(final TickEvent.PlayerTickEvent e) {
        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSprint.getKeyCode(), true);
    }
    
    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSprint.getKeyCode(), false);
    }
}
